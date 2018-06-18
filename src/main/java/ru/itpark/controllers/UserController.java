package ru.itpark.controllers;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Parameter;
import org.hibernate.boot.jaxb.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itpark.dto.UserDto;
import ru.itpark.forms.PasswordForm;
import ru.itpark.forms.UserForm;
import ru.itpark.models.Post;
import ru.itpark.models.Requesting;
import ru.itpark.models.SupportInfo;
import ru.itpark.models.User;
import ru.itpark.repositories.UserRepositori;
import ru.itpark.security.details.UserDetailsImpl;
import ru.itpark.service.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/register")
    public String getSignUpPage(ModelMap modelMap){
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
        UserDetailsImpl details = (UserDetailsImpl)authentication.getPrincipal();
        User user = details.getUser();
        UserDto userDto = UserDto.dtoUserFromUser(userRepositori.findOne(user.getId()));
        modelMap.addAttribute("user",userDto);
        List<Post> reverseList = postService.reverseList(postService.getPostsUserTo(userDto.getId()));
        for (Post post: reverseList){
            post.setOwnerPostDto(UserDto.dtoUserFromUser(post.getOwnerPost()));
        }
        modelMap.addAttribute("posts", reverseList);
        List<User> friends = new ArrayList<>();
        friends.add(userRepositori.findOne(3L));
        friends.add(userRepositori.findOne(4L));
        friends.add(userRepositori.findOne(7L));
        friends.add(userRepositori.findOne(14L));
        friends.add(userRepositori.findOne(2L));
        friends.add(userRepositori.findOne(15L));
        friends.add(userRepositori.findOne(16L));
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
        SupportInfo info = SupportInfo.builder()
                .friends(friends.size())
                .subscribers(userDto.getInputRequestings().size())
                .posts(reverseList.size())
                .chats(userDto.getChats().size())
                .build();
        modelMap.addAttribute("info", info);
        Integer newRequestings = requestingService.getNewRequesting(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);
        return "profile";
    }

    @GetMapping("/edit")
    public String getSignUpPage(ModelMap modelMap, Authentication authentication){
        UserDetailsImpl details = (UserDetailsImpl)authentication.getPrincipal();
        UserDto userDto = UserDto.dtoUserFromUser(details.getUser());
        modelMap.addAttribute("user",userDto);
        return "edit";
    }

    @PostMapping("/edit")
    public String postSignUpPage(@ModelAttribute UserForm userForm, Authentication authentication){
        userService.changeData(userForm, authentication);
        return "redirect:/profile";
    }

    @GetMapping("/profile/friends")
    public String getFriendsPage(ModelMap modelMap, Authentication authentication){
        User user = userService.getUserInfo(authentication);
        UserDto userDto = UserDto.dtoUserFromUser(userRepositori.findOne(user.getId()));
        modelMap.addAttribute("user",userDto);
        modelMap.addAttribute("users", userDto.getFriends());
        Integer newRequestings = requestingService.getNewRequesting(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);
        return "friends";
    }

    @GetMapping("/users")
    public String getUsersPage(ModelMap modelMap, Authentication authentication){
        User user = userService.getUserInfo(authentication);
        UserDto userDto = UserDto.dtoUserFromUser(userRepositori.findOne(user.getId()));
        modelMap.addAttribute("user",userDto);
        List<User> users = userRepositori.findAllByIdIsNot(userDto.getId());
        List<UserDto> usersDto = new ArrayList<>();
        for (User userFor: users){
            usersDto.add(UserDto.dtoUserFromUser(userFor));
        }
        modelMap.addAttribute("users", usersDto);
        modelMap.addAttribute("user",userDto);
        Integer newRequestings = requestingService.getNewRequesting(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);
        return "users";
    }

    @PostMapping("/users/find")
    public String postUsersFind(@RequestParam ("paramFind") String paramFind,
                                @RequestParam ("city_hidden") String paramCity,
                                @RequestParam ("dataBirthday_hidden") String paramDataBirthday,
                                ModelMap modelMap,
                                Authentication authentication){
//        String paramFind = request.getParameter("paramFind");
//        String paramCity = request.getParameter("city_hidden");
//        String paramDataBirthday = request.getParameter("dataBirthday_hidden");
        User user = userService.getUserInfo(authentication);
        UserDto userDto = UserDto.dtoUserFromUser(userRepositori.findOne(user.getId()));
        List<User> users = userRepositori.findAllByIdIsNot(userDto.getId());
        List<UserDto> usersDto = new ArrayList<>();
        Integer newRequestings = requestingService.getNewRequesting(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);

        if (paramFind == null){
            for (User userFor: users){
                if(paramCity != "" ){
                    System.out.println("1");
                    if(userFor.getCity().contains(paramCity)){
                        usersDto.add(UserDto.dtoUserFromUser(userFor));
                    }
                }
                else if(paramDataBirthday != null && paramCity == null){
//                    if(userFor.getDataBirthday().isEqual()){
//                        usersDto.add(UserDto.dtoUserFromUser(userFor));
//                    }
                    System.out.println("2");
                }
                else if(paramCity != null && paramDataBirthday != null){
//                    if(userFor.getDataBirthday().isEqual() && (userFor.getCity().contains(paramCity) || paramCity.contains(userFor.getCity()))){
//                        usersDto.add(UserDto.dtoUserFromUser(userFor));
//
//                    }
                    System.out.println("3");
                }
                else{
                    usersDto.add(UserDto.dtoUserFromUser(userFor));
                    System.out.println("4");
                }
            }
            modelMap.addAttribute("user",userDto);
            modelMap.addAttribute("users", usersDto);
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
                modelMap.addAttribute("users", usersDto);
                modelMap.addAttribute("user", userDto);
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
                modelMap.addAttribute("users", usersDto);
                modelMap.addAttribute("user", userDto);
                return "users";
            }
        }
    }

    @GetMapping("/users/{id-candidate}")
    public String getCandidatePage(ModelMap modelMap, Authentication authentication,
                                @PathVariable("id-candidate") Long idCandidate){
        if(idCandidate == userService.getUserInfo(authentication).getId()) return "redirect:/profile";
        User user = userService.getUserInfo(authentication);
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
        if(requestingService.getStatus(user, userCandidate)) {
            modelMap.addAttribute("status", "Вы подписаны");
        }

        List<User> friends = new ArrayList<>();
        friends.add(userRepositori.findOne(3L));
        friends.add(userRepositori.findOne(4L));
        friends.add(userRepositori.findOne(7L));
        friends.add(userRepositori.findOne(14L));
        friends.add(userRepositori.findOne(2L));
        friends.add(userRepositori.findOne(15L));
        friends.add(userRepositori.findOne(16L));
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
        SupportInfo info = SupportInfo.builder()
                .friends(friends.size())
                .subscribers(candidateDto.getInputRequestings().size())
                .posts(reverseList.size())
                .chats(candidateDto.getChats().size())
                .build();
        modelMap.addAttribute("info", info);
        Integer newRequestings = requestingService.getNewRequesting(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);
        return "profileCandidate";
    }

}



