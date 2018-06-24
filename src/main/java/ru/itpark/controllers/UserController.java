package ru.itpark.controllers;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Parameter;
import org.hibernate.boot.jaxb.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itpark.dto.UserDto;
import ru.itpark.forms.PasswordForm;
import ru.itpark.forms.UserForm;
import ru.itpark.models.*;
import ru.itpark.repositories.CityRepository;
import ru.itpark.repositories.UserRepositori;
import ru.itpark.security.details.UserDetailsImpl;
import ru.itpark.service.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Transactional
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositori userRepositori;

    @Autowired
    private PostService postService;

    @Autowired
    private RequestingService requestingService;

    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/register")
    public String getSignUpPage(ModelMap modelMap){
        List<City> cities = cityRepository.findAll();
        modelMap.addAttribute("cities", cities);
        return "register";
    }

    @PostMapping("/register")
    public String postSignUpPage(@ModelAttribute UserForm userForm){
        userService.registerNewUser(userForm);
        emailService.sendMail("Регистрация прошла успешно!", "Регистрация \"Друзья\"", userForm.getEMail());
        return "redirect:/logout";
    }

    @GetMapping("/login")
    public String getLoginPage(HttpServletRequest request, ModelMap modelMap, Authentication authentication) {
        if(authentication != null){
            return"redirect:/profile";
        }
        if (request.getParameterMap().containsKey("error")) {
            modelMap.addAttribute("error", true);
        }
        return "login";
    }

    @GetMapping("/profile")
    public String getProfilePage(ModelMap modelMap, Authentication authentication, HttpServletRequest request){
        if(authentication == null){
            return "redirect:/login";
        }
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user",userDto);
        if (user.getRole().equals(Role.ADMIN)) return "deleted";
        List<Post> reverseList = postService.reverseList(postService.getPostsUserTo(userDto.getId()));
        for (Post post: reverseList){
            post.setOwnerPostDto(UserDto.dtoUserFromUser(post.getOwnerPost()));
        }
        modelMap.addAttribute("posts", reverseList);
        List<User> friends = new ArrayList<>();
        friends.addAll(userDto.getFriends());
        friends.addAll(userDto.getFriendOf());
        int countFriends = friends.size();
        Random random = new Random();
        if(countFriends > 6){
            List<Integer> countList = new ArrayList<>();
            int count = 0;
            for (int i = 0; i < 6;){
                count = random.nextInt(countFriends);
                if (i >= 1){
                    while(countList.contains(count)){
                        count = random.nextInt(countFriends);
                    }
                    countList.add(count);
                    modelMap.addAttribute("user" + (++i), UserDto.dtoUserFromUser(friends.get(count)));
                }
                else{
                    modelMap.addAttribute("user" + (++i), UserDto.dtoUserFromUser(friends.get(count)));
                    countList.add(count);
                }
            }
        }
        else if ( countFriends < 6 && countFriends != 0){
            List<Integer> countList = new ArrayList<>();
            int count = 0;
            for (int i = 0; i < countFriends;){
                count = random.nextInt(countFriends);
                if (i >= 1){
                    while(countList.contains(count)){
                        count = random.nextInt(countFriends);
                    }
                    countList.add(count);
                    modelMap.addAttribute("user" + (++i), UserDto.dtoUserFromUser(friends.get(count)));
                }
                else{
                    modelMap.addAttribute("user" + (++i), UserDto.dtoUserFromUser(friends.get(count)));
                    countList.add(count);
                }
            }
        }
        else if(countFriends == 0){
            modelMap.addAttribute("noFriends", new Mess("Пока нет друзей"));
        }
        SupportInfo info = SupportInfo.builder()
                .friends(friends.size())
                .subscribers(0)
                .posts(reverseList.size())
                .chats(userDto.getChats().size())
                .build();
        if(requestingService.getInputRequests(user).isPresent()) info.setSubscribers(requestingService.getInputRequests(user).get().size());
        modelMap.addAttribute("info", info);
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);
        return "profile";
    }

    @GetMapping("/edit")
    public String getSignUpPage(ModelMap modelMap, Authentication authentication){
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user",userDto);
        List<City> cities = cityRepository.findAll();
        modelMap.addAttribute("cities", cities);
        return "edit";
    }

    @PostMapping("/edit")
    public String postSignUpPage(@ModelAttribute UserForm userForm, Authentication authentication){
        userService.changeData(userForm, authentication);
        return "redirect:/profile";
    }

    @GetMapping("/profile/friends")
    public String getFriendsPage(ModelMap modelMap, Authentication authentication){
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user",userDto);
        List<User> friends = new ArrayList<>();
        friends.addAll(userDto.getFriends());
        friends.addAll(userDto.getFriendOf());
        if(friends.size() != 0){
            List<UserDto> usersDto = new ArrayList<>();
            for (User userFor: friends){
                usersDto.add(UserDto.dtoUserFromUser(userFor));
            }
            modelMap.addAttribute("users", usersDto);
        }
        else modelMap.addAttribute("message", new Mess("У вас пока нет друзей"));
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);
        List<City> cities = cityRepository.findAll();
        modelMap.addAttribute("cities", cities);
        return "friends";
    }

    @PostMapping("/profile/friends/find")
    public String getFriendsFind(@RequestParam ("paramFind") String paramFind,
                                 @RequestParam ("paramCity") String paramCity,
                                 @RequestParam ("paramDataBirthday") String paramDataBirthday,
                                 ModelMap modelMap,
                                 Authentication authentication){
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user",userDto);
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);
        List<City> cities = cityRepository.findAll();
        modelMap.addAttribute("cities", cities);
        List<User> friends = new ArrayList<>();
        friends.addAll(userDto.getFriends());
        friends.addAll(userDto.getFriendOf());
        if(friends.size() != 0){
            if (paramFind == ""){
                List<UserDto> usersDto = new ArrayList<>();
                for (User friend : friends){
                    if(paramCity != "" && paramDataBirthday == ""){
                        if(friend.getCity().contains(paramCity) || paramCity.contains(friend.getCity()) || friend.getCity().equals(paramCity) ){
                            usersDto.add(UserDto.dtoUserFromUser(friend));
                        }
                    }
                    else if(paramDataBirthday != "" && paramCity == ""){
                        if(friend.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday))){
                            usersDto.add(UserDto.dtoUserFromUser(friend));
                        }
                    }
                    else if(paramCity != "" && paramDataBirthday != ""){
                        if(friend.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday)) && (friend.getCity().contains(paramCity) || paramCity.contains(friend.getCity()))){
                            usersDto.add(UserDto.dtoUserFromUser(friend));
                        }
                    }
                    else if(paramCity == "" && paramDataBirthday == ""){
                        usersDto.add(UserDto.dtoUserFromUser(friend));
                    }
                }
                if(usersDto.size() != 0)modelMap.addAttribute("users", usersDto);
                else modelMap.addAttribute("message", new Mess("Ваш запрос не дал результатов"));
                return "friends";
            }

            else {
                String[] arrStr = paramFind.split("\\s+");
                if(arrStr.length==2) {
                    String paramFindOne = arrStr[0];
                    String paramFindTwo = arrStr[1];

                    List<UserDto> usersDto= new ArrayList<>();
                    for(User friend : friends){
                        if(friend.getFirstName().contains(paramFindOne) || friend.getLastName().contains(paramFindTwo)
                                || friend.getLastName().contains(paramFindOne) || friend.getFirstName().contains(paramFindTwo)
                                || paramFindOne.contains(friend.getFirstName()) || paramFindOne.contains(friend.getLastName())
                                || paramFindTwo.contains(friend.getFirstName()) || paramFindTwo.contains(friend.getLastName()))
                        {
                            usersDto.add(UserDto.dtoUserFromUser(friend));
                        }
                    }

                    List<UserDto> usersDtoTemp = new ArrayList<>();
                    for(UserDto userDtoFor : usersDto){
                        if(paramCity != "" && paramDataBirthday == ""){
                            if(userDtoFor.getCity().contains(paramCity) || paramCity.contains(userDtoFor.getCity()) || userDtoFor.getCity().equals(paramCity) ){
                                usersDto.add(userDtoFor);
                            }
                        }
                        else if(paramDataBirthday != "" && paramCity == ""){
                            if(userDtoFor.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday))){
                                usersDtoTemp.add(userDtoFor);
                            }
                        }
                        else if(paramCity != "" && paramDataBirthday != ""){
                            if(userDtoFor.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday)) && (userDtoFor.getCity().contains(paramCity) || paramCity.contains(userDtoFor.getCity()))){
                                usersDtoTemp.add(userDtoFor);
                            }
                        }
                        else if(paramCity == "" && paramDataBirthday == ""){
                            usersDtoTemp.add(userDtoFor);
                        }
                    }
                    if(usersDtoTemp.size() != 0)modelMap.addAttribute("users", usersDtoTemp);
                    else modelMap.addAttribute("message", new Mess("Ваш запрос не дал результатов"));
                    return "friends";
                }

                else if(arrStr.length == 0){
                    List<UserDto> usersDto= new ArrayList<>();
                    for(User friend: friends){
                        usersDto.add(UserDto.dtoUserFromUser(friend));
                    }
                    modelMap.addAttribute("user",userDto);
                    if(usersDto.size() != 0)modelMap.addAttribute("users", usersDto);
                    else modelMap.addAttribute("message", new Mess("Ваш запрос не дал результатов"));
                    return "friends";
                }

                else{
                    String paramFindOne = arrStr[0];

                    List<UserDto> usersDto= new ArrayList<>();

                    for(User friend : friends){
                        if(friend.getFirstName().contains(paramFindOne) || friend.getLastName().contains(paramFindOne)
                                || paramFindOne.contains(friend.getFirstName()) || paramFindOne.contains(friend.getLastName()))
                        {
                            usersDto.add(UserDto.dtoUserFromUser(friend));
                        }
                    }

                    List<UserDto> usersDtoTemp = new ArrayList<>();
                    for(UserDto userDtoFor : usersDto){
                        if(paramCity != "" && paramDataBirthday == ""){
                            if(userDtoFor.getCity().contains(paramCity) || paramCity.contains(userDtoFor.getCity()) || userDtoFor.getCity().equals(paramCity) ){
                                usersDtoTemp.add(userDtoFor);
                            }
                        }
                        else if(paramDataBirthday != "" && paramCity == ""){
                            if(userDtoFor.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday))){
                                usersDtoTemp.add(userDtoFor);
                            }
                        }
                        else if(paramCity != "" && paramDataBirthday != ""){
                            if(userDtoFor.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday)) && (userDtoFor.getCity().contains(paramCity) || paramCity.contains(userDtoFor.getCity()))){
                                usersDtoTemp.add(userDtoFor);
                            }
                        }
                        else if(paramCity == "" && paramDataBirthday == ""){
                            usersDtoTemp.add(userDtoFor);
                        }
                    }
                    if(usersDtoTemp.size() != 0)modelMap.addAttribute("users", usersDtoTemp);
                    else modelMap.addAttribute("message", new Mess("Ваш запрос не дал результатов"));
                    modelMap.addAttribute("user", userDto);
                    return "friends";
                }
            }
        }
        else modelMap.addAttribute("message", new Mess("У вас пока нет друзей"));
        return "friends";
    }


    @GetMapping("/users/{id-candidate}/friends")
    public String getCandidateFriendsPage(ModelMap modelMap, Authentication authentication,
                                          @PathVariable(name = "id-candidate") Long idCandidate){
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user",userDto);
        User candidate = userRepositori.findOne(idCandidate);
        UserDto candidateDto = UserDto.dtoUserFromUser(candidate);
        modelMap.addAttribute("candidate", candidateDto);
        List<User> friends = new ArrayList<>();
        friends.addAll(candidateDto.getFriends());
        friends.addAll(candidateDto.getFriendOf());
        if(friends.size() != 0){
            List<UserDto> usersDto = new ArrayList<>();
            for (User userFor: friends){
                UserDto userForDto = UserDto.dtoUserFromUser(userFor);
                if(userForDto.getId() != userDto.getId()){
                    userForDto.setStatus(requestingService.getStatus(user, userFor));
                }
                usersDto.add(userForDto);
                if(userForDto.getId() == userDto.getId()) userForDto.setCondition("true");
            }
            modelMap.addAttribute("users", usersDto);
        }
        else modelMap.addAttribute("message", new Mess("Пока нет друзей"));
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);
        List<City> cities = cityRepository.findAll();
        modelMap.addAttribute("cities", cities);
        return "friendsCandidate";
    }

    @PostMapping("/users/{id-candidate}/friends/find")
    public String getCandidateFriendsFind(@RequestParam ("paramFind") String paramFind,
                                 @RequestParam ("paramCity") String paramCity,
                                 @RequestParam ("paramDataBirthday") String paramDataBirthday,
                                 @PathVariable("id-candidate") Long idCandidate,
                                 ModelMap modelMap,
                                 Authentication authentication){
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user",userDto);
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);
        List<City> cities = cityRepository.findAll();
        modelMap.addAttribute("cities", cities);
        User candidate = userRepositori.findOne(idCandidate);
        UserDto candidateDto = UserDto.dtoUserFromUser(candidate);
        modelMap.addAttribute("candidate", candidateDto);
        List<User> friends = new ArrayList<>();
        friends.addAll(candidateDto.getFriends());
        friends.addAll(candidateDto.getFriendOf());
        if(friends.size() != 0){
            if (paramFind == ""){
                List<UserDto> usersDto = new ArrayList<>();
                for (User friend : friends){
                    if(paramCity != "" && paramDataBirthday == ""){
                        if(friend.getCity().contains(paramCity) || paramCity.contains(friend.getCity()) || friend.getCity().equals(paramCity) ){
                            usersDto.add(UserDto.dtoUserFromUser(friend));
                        }
                    }
                    else if(paramDataBirthday != "" && paramCity == ""){
                        if(friend.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday))){
                            usersDto.add(UserDto.dtoUserFromUser(friend));
                        }
                    }
                    else if(paramCity != "" && paramDataBirthday != ""){
                        if(friend.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday)) && (friend.getCity().contains(paramCity) || paramCity.contains(friend.getCity()))){
                            usersDto.add(UserDto.dtoUserFromUser(friend));
                        }
                    }
                    else if(paramCity == "" && paramDataBirthday == ""){
                        usersDto.add(UserDto.dtoUserFromUser(friend));
                    }
                }
                if(usersDto.size() != 0){
                    for(UserDto usr : usersDto){
                        if(usr.getId() != userDto.getId()){
                            usr.setStatus(requestingService.getStatus(user, userRepositori.findOne(usr.getId())));
                        }
                        if(usr.getId() == userDto.getId()){
                            usr.setCondition("true");
                        }
                    }
                    modelMap.addAttribute("users", usersDto);
                }
                else modelMap.addAttribute("message", new Mess("Ваш запрос не дал результатов"));
                return "friendsCandidate";
            }

            else {
                String[] arrStr = paramFind.split("\\s+");
                if(arrStr.length==2) {
                    String paramFindOne = arrStr[0];
                    String paramFindTwo = arrStr[1];

                    List<UserDto> usersDto= new ArrayList<>();
                    for(User friend : friends){
                        if(friend.getFirstName().contains(paramFindOne) || friend.getLastName().contains(paramFindTwo)
                                || friend.getLastName().contains(paramFindOne) || friend.getFirstName().contains(paramFindTwo)
                                || paramFindOne.contains(friend.getFirstName()) || paramFindOne.contains(friend.getLastName())
                                || paramFindTwo.contains(friend.getFirstName()) || paramFindTwo.contains(friend.getLastName()))
                        {
                            usersDto.add(UserDto.dtoUserFromUser(friend));
                        }
                    }

                    List<UserDto> usersDtoTemp = new ArrayList<>();
                    for(UserDto userDtoFor : usersDto){
                        if(paramCity != "" && paramDataBirthday == ""){
                            if(userDtoFor.getCity().contains(paramCity) || paramCity.contains(userDtoFor.getCity()) || userDtoFor.getCity().equals(paramCity) ){
                                usersDto.add(userDtoFor);
                            }
                        }
                        else if(paramDataBirthday != "" && paramCity == ""){
                            if(userDtoFor.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday))){
                                usersDtoTemp.add(userDtoFor);
                            }
                        }
                        else if(paramCity != "" && paramDataBirthday != ""){
                            if(userDtoFor.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday)) && (userDtoFor.getCity().contains(paramCity) || paramCity.contains(userDtoFor.getCity()))){
                                usersDtoTemp.add(userDtoFor);
                            }
                        }
                        else if(paramCity == "" && paramDataBirthday == ""){
                            usersDtoTemp.add(userDtoFor);
                        }
                    }
                    if(usersDtoTemp.size() != 0){
                        for(UserDto usr : usersDtoTemp){
                            if(usr.getId() != userDto.getId()){
                                usr.setStatus(requestingService.getStatus(user, userRepositori.findOne(usr.getId())));
                            }
                            if(usr.getId() == userDto.getId()){
                                usr.setCondition("true");
                            }
                        }
                        modelMap.addAttribute("users", usersDtoTemp);
                    }
                    else modelMap.addAttribute("message", new Mess("Ваш запрос не дал результатов"));
                    return "friendsCandidate";
                }

                else if(arrStr.length == 0){
                    List<UserDto> usersDto= new ArrayList<>();
                    for(User friend: friends){
                        usersDto.add(UserDto.dtoUserFromUser(friend));
                    }
                    modelMap.addAttribute("user",userDto);
                    if(usersDto.size() != 0){
                        for(UserDto usr : usersDto){
                            if(usr.getId() != userDto.getId()){
                                usr.setStatus(requestingService.getStatus(user, userRepositori.findOne(usr.getId())));
                            }
                            if(usr.getId() == userDto.getId()){
                                usr.setCondition("true");
                            }
                        }
                        modelMap.addAttribute("users", usersDto);
                    }
                    else modelMap.addAttribute("message", new Mess("Ваш запрос не дал результатов"));
                    return "friendsCandidate";
                }

                else{
                    String paramFindOne = arrStr[0];

                    List<UserDto> usersDto= new ArrayList<>();

                    for(User friend : friends){
                        if(friend.getFirstName().contains(paramFindOne) || friend.getLastName().contains(paramFindOne)
                                || paramFindOne.contains(friend.getFirstName()) || paramFindOne.contains(friend.getLastName()))
                        {
                            usersDto.add(UserDto.dtoUserFromUser(friend));
                        }
                    }

                    List<UserDto> usersDtoTemp = new ArrayList<>();
                    for(UserDto userDtoFor : usersDto){
                        if(paramCity != "" && paramDataBirthday == ""){
                            if(userDtoFor.getCity().contains(paramCity) || paramCity.contains(userDtoFor.getCity()) || userDtoFor.getCity().equals(paramCity) ){
                                usersDtoTemp.add(userDtoFor);
                            }
                        }
                        else if(paramDataBirthday != "" && paramCity == ""){
                            if(userDtoFor.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday))){
                                usersDtoTemp.add(userDtoFor);
                            }
                        }
                        else if(paramCity != "" && paramDataBirthday != ""){
                            if(userDtoFor.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday)) && (userDtoFor.getCity().contains(paramCity) || paramCity.contains(userDtoFor.getCity()))){
                                usersDtoTemp.add(userDtoFor);
                            }
                        }
                        else if(paramCity == "" && paramDataBirthday == ""){
                            usersDtoTemp.add(userDtoFor);
                        }
                    }

                    if(usersDtoTemp.size() != 0){
                        for(UserDto usr : usersDtoTemp){
                            if(usr.getId() != userDto.getId()){
                                usr.setStatus(requestingService.getStatus(user, userRepositori.findOne(usr.getId())));
                            }
                            if(usr.getId() == userDto.getId()){
                                usr.setCondition("true");
                            }
                        }
                        modelMap.addAttribute("users", usersDtoTemp);
                    }
                    else modelMap.addAttribute("message", new Mess("Ваш запрос не дал результатов"));
                    modelMap.addAttribute("user", userDto);
                    return "friendsCandidate";
                }
            }
        }
        else modelMap.addAttribute("message", new Mess("Пока нет друзей"));
        return "friendsCandidate";
    }

    @GetMapping("/users")
    public String getUsersPage(ModelMap modelMap, Authentication authentication){
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user",userDto);
        List<User> users = userRepositori.findAllByIdIsNot(userDto.getId());
        List<UserDto> usersDto = new ArrayList<>();
        for (User userFor: users){
            String status = requestingService.getStatus(user, userFor);
            UserDto userForDto = UserDto.dtoUserFromUser(userFor);
            userForDto.setStatus(status);
            usersDto.add(userForDto);
        }
        modelMap.addAttribute("users", usersDto);
        modelMap.addAttribute("user",userDto);
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);
        List<City> cities = cityRepository.findAll();
        modelMap.addAttribute("cities", cities);
        return "users";
    }

    @PostMapping("/users/find")
    public String postUsersFind(@RequestParam ("paramFind") String paramFind,
                                @RequestParam ("paramCity") String paramCity,
                                @RequestParam ("paramDataBirthday") String paramDataBirthday,
                                ModelMap modelMap,
                                Authentication authentication){
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        List<User> users = userRepositori.findAllByIdIsNot(userDto.getId());
        List<UserDto> usersDto = new ArrayList<>();
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);

        List<City> cities = cityRepository.findAll();
        modelMap.addAttribute("cities", cities);

        if (paramFind == ""){
            for (User userFor: users){
                if(paramCity != "" && paramDataBirthday == ""){
                    if(userFor.getCity().contains(paramCity) || paramCity.contains(userFor.getCity()) || userFor.getCity().equals(paramCity) ){
                        usersDto.add(UserDto.dtoUserFromUser(userFor));
                    }
                }
                else if(paramDataBirthday != "" && paramCity == ""){
                    if(userFor.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday))){
                        usersDto.add(UserDto.dtoUserFromUser(userFor));
                    }
                }
                else if(paramCity != "" && paramDataBirthday != ""){
                    if(userFor.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday)) && (userFor.getCity().contains(paramCity) || paramCity.contains(userFor.getCity()))){
                        usersDto.add(UserDto.dtoUserFromUser(userFor));
                    }
                }
                else if(paramCity == "" && paramDataBirthday == ""){
                    usersDto.add(UserDto.dtoUserFromUser(userFor));
                }
            }
            modelMap.addAttribute("user",userDto);
            if(usersDto.size() != 0)modelMap.addAttribute("users", usersDto);
            return "users";
        }
        else {

            String[] arrStr = paramFind.split("\\s+");
            if(arrStr.length==2) {
                String paramFindOne = arrStr[0];
                String paramFindTwo = arrStr[1];
                List<User> listFindOne = userRepositori.findUsersByFirstNameContainsOrLastNameContains(paramFindOne, paramFindTwo);
                List<User> listFindTwo = userRepositori.findUsersByFirstNameContainsOrLastNameContains(paramFindTwo, paramFindOne);
                for (User userFor : listFindOne) {
                    if (listFindTwo.contains(userFor)) listFindTwo.remove(userFor);
                }
                listFindOne.addAll(listFindTwo);
                if (listFindOne.contains(userService.getUserInfo(authentication)))
                    listFindOne.remove(userService.getUserInfo(authentication));
                for (User userFor: listFindOne){
                    usersDto.add(UserDto.dtoUserFromUser(userFor));
                }

                List<UserDto> usersDtoTemp = new ArrayList<>();
                for(UserDto userDtoFor : usersDto){
                    if(paramCity != "" && paramDataBirthday == ""){
                        if(userDtoFor.getCity().contains(paramCity) || paramCity.contains(userDtoFor.getCity()) || userDtoFor.getCity().equals(paramCity) ){
                            usersDtoTemp.add(userDtoFor);
                        }
                    }
                    else if(paramDataBirthday != "" && paramCity == ""){
                        if(userDtoFor.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday))){
                            usersDtoTemp.add(userDtoFor);
                        }
                    }
                    else if(paramCity != "" && paramDataBirthday != ""){
                        if(userDtoFor.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday)) && (userDtoFor.getCity().contains(paramCity) || paramCity.contains(userDtoFor.getCity()))){
                            usersDtoTemp.add(userDtoFor);
                        }
                    }
                    else if(paramCity == "" && paramDataBirthday == ""){
                        usersDtoTemp.add(userDtoFor);
                    }
                }
                if(usersDtoTemp.size() != 0)modelMap.addAttribute("users", usersDtoTemp);
                modelMap.addAttribute("user", userDto);
                return "users";
            }

            else if(arrStr.length == 0){
                for(User userFor: users){
                    usersDto.add(UserDto.dtoUserFromUser(userFor));
                }
                modelMap.addAttribute("user",userDto);
                modelMap.addAttribute("users", usersDto);
                return "users";
            }

            else{
                String paramFindOne = arrStr[0];
                List<User> listFindOne = userRepositori.findUsersByFirstNameContains(paramFindOne);
                List<User> listFindTwo = userRepositori.findUsersByLastNameContains(paramFindOne);
                for (User userFor : listFindOne) {
                    if (listFindTwo.contains(userFor)) listFindTwo.remove(userFor);
                }
                listFindOne.addAll(listFindTwo);
                if (listFindOne.contains(userService.getUserInfo(authentication)))
                    listFindOne.remove(userService.getUserInfo(authentication));
                for (User userFor: listFindOne){
                    usersDto.add(UserDto.dtoUserFromUser(userFor));
                }

                List<UserDto> usersDtoTemp = new ArrayList<>();
                for(UserDto userDtoFor : usersDto){
                    if(paramCity != "" && paramDataBirthday == ""){
                        if(userDtoFor.getCity().contains(paramCity) || paramCity.contains(userDtoFor.getCity()) || userDtoFor.getCity().equals(paramCity) ){
                            usersDtoTemp.add(userDtoFor);
                        }
                    }
                    else if(paramDataBirthday != "" && paramCity == ""){
                        if(userDtoFor.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday))){
                            usersDtoTemp.add(userDtoFor);
                        }
                    }
                    else if(paramCity != "" && paramDataBirthday != ""){
                        if(userDtoFor.getDataBirthday().isEqual(LocalDate.parse(paramDataBirthday)) && (userDtoFor.getCity().contains(paramCity) || paramCity.contains(userDtoFor.getCity()))){
                            usersDtoTemp.add(userDtoFor);
                        }
                    }
                    else if(paramCity == "" && paramDataBirthday == ""){
                        usersDtoTemp.add(userDtoFor);
                    }
                }
                if(usersDtoTemp.size() != 0)modelMap.addAttribute("users", usersDtoTemp);
                modelMap.addAttribute("user", userDto);
                return "users";
            }
        }
    }

    @GetMapping("/users/{id-candidate}")
    public String getCandidatePage(ModelMap modelMap, Authentication authentication,
                                @PathVariable("id-candidate") Long idCandidate){
        if(idCandidate == userService.getUserInfo(authentication).getId()) return "redirect:/profile";
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user", userDto);
        User userCandidate = userService.getUserById(idCandidate);
        UserDto candidateDto = UserDto.dtoUserFromUser(userCandidate);
        modelMap.addAttribute("candidate", candidateDto);
        List<Post> reverseList = postService.reverseList(postService.getPostsUserTo(candidateDto.getId()));
        for (Post post: reverseList){
            post.setOwnerPostDto(UserDto.dtoUserFromUser(post.getOwnerPost()));
        }
        modelMap.addAttribute("posts", reverseList);
        String status = requestingService.getStatus(user, userCandidate);
        modelMap.addAttribute("status", status);
        if (userCandidate.getRole().equals(Role.ADMIN)) return "deletedCandidate";
        List<User> friends = new ArrayList<>();
        friends.addAll(candidateDto.getFriends());
        friends.addAll(candidateDto.getFriendOf());
        int countFriends = friends.size();
        Random random = new Random();
        if(countFriends > 6){
            List<Integer> countList = new ArrayList<>();
            int count = 0;
            for (int i = 0; i < 6;){
                count = random.nextInt(countFriends);
                if (i >= 1){
                    while(countList.contains(count)){
                        count = random.nextInt(countFriends);
                    }
                    countList.add(count);
                    modelMap.addAttribute("user" + (++i), UserDto.dtoUserFromUser(friends.get(count)));
                }
                else{
                    modelMap.addAttribute("user" + (++i), UserDto.dtoUserFromUser(friends.get(count)));
                    countList.add(count);
                }
            }
        }
        else if ( countFriends < 6 && countFriends != 0){
            List<Integer> countList = new ArrayList<>();
            int count = 0;
            for (int i = 0; i < countFriends;){
                count = random.nextInt(countFriends);
                if (i >= 1){
                    while(countList.contains(count)){
                        count = random.nextInt(countFriends);
                    }
                    countList.add(count);
                    modelMap.addAttribute("user" + (++i), UserDto.dtoUserFromUser(friends.get(count)));
                }
                else{
                    modelMap.addAttribute("user" + (++i), UserDto.dtoUserFromUser(friends.get(count)));
                    countList.add(count);
                }
            }
        }
        else if(countFriends == 0){
            modelMap.addAttribute("noFriends", new Mess("Пока нет друзей"));
        }
        SupportInfo info = SupportInfo.builder()
                .friends(friends.size())
                .subscribers(0)
                .posts(reverseList.size())
                .chats(candidateDto.getChats().size())
                .build();
        if(requestingService.getInputRequests(userCandidate).isPresent()) info.setSubscribers(requestingService.getInputRequests(userCandidate).get().size());
        modelMap.addAttribute("info", info);
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);
        return "profileCandidate";
    }

}



