package ru.itpark.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itpark.dto.UserDto;
import ru.itpark.models.*;
import ru.itpark.repositories.PostRepository;
import ru.itpark.repositories.RequestingRepository;
import ru.itpark.repositories.UserRepositori;
import ru.itpark.security.details.UserDetailsImpl;
import ru.itpark.service.PostService;
import ru.itpark.service.RequestingService;
import ru.itpark.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class SupportController {

    @Autowired
    private UserRepositori userRepositori;

    @Autowired
    private RequestingRepository requestingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private RequestingService requestingService;

    @GetMapping("/")
    public String getIndexPage(Authentication authentication) {
        if(authentication == null)return "index";
        else return "redirect:/profile";
    }

    @PostMapping("/checkLogin")
    public ResponseEntity<Object> postCheckLogin(@RequestParam(name = "login") String login){
        Optional<User> optional = userRepositori.findOneByLogin(login);
        if(optional.isPresent()){
            return ResponseEntity.ok(new Mess("true"));
        }
        return ResponseEntity.ok(new Mess("false"));
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<Object>  postCheckEmail(@RequestParam(name = "eMail") String eMail){
        Optional<User> optional = userRepositori.findOneByEMail(eMail);
        if(optional.isPresent()){
            return ResponseEntity.ok(new Mess("true"));
        }
        return ResponseEntity.ok(new Mess("false"));
    }

    @GetMapping("/{id-candidate}/sendRequest")
    public String postSendRequst(@PathVariable("id-candidate") Long idCandidate, Authentication authentication, HttpServletRequest httpServletRequest){
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        User candidate = userRepositori.findOneById(idCandidate).orElseThrow(IllegalArgumentException::new);
        //Замена местами user и candidate
        if(requestingService.getNewRequest(candidate, user).isPresent()) return "redirect:/profile/friends";
        if (requestingService.findRequest(user, candidate).isPresent()){
            Requesting request = requestingService.findRequest(user, candidate).get();
            if (request.getRoleRequesting().equals(RoleRequesting.UNSUBSCRIBE)) {
                request.setRoleRequesting(RoleRequesting.CANCEL);
                requestingRepository.save(request);
                return "redirect:/profile/friends";
            }
            else if(request.getRoleRequesting().equals(RoleRequesting.CANCEL)){
                return "redirect:/profile/friends";
            }
            else if(request.getRoleRequesting().equals(RoleRequesting.TAKE)){
                return "redirect:/profile/friends";
            }
            else if(request.getRoleRequesting().equals(RoleRequesting.NEW)){
                return "redirect:/profile/friends";
            }
        }
        Requesting requesting  = Requesting.builder()
                .inputUser(candidate)
                .outputUser(user)
                .roleRequesting(RoleRequesting.NEW)
                .build();
        requestingRepository.save(requesting);
        return "redirect:/profile/friends";
    }

    @GetMapping("/{id-candidate}/confirmRequest")
    public String postConfirmRequst(@PathVariable(name = "id-candidate") Long idCandidate, Authentication authentication, HttpServletRequest httpServletRequest){
        System.out.println(httpServletRequest.getRequestURL());
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        User candidate = userRepositori.findOneById(idCandidate).orElseThrow(IllegalArgumentException::new);
        if(requestingService.getConfirmedRequest(user, candidate).isPresent()) return "redirect:/profile/friends";
        if(requestingService.findRequest(user, candidate).isPresent()) {
            Requesting request = requestingService.findRequest(user, candidate).get();
            if (request.getRoleRequesting().equals(RoleRequesting.CANCEL)) {
                request.setRoleRequesting(RoleRequesting.TAKE);
                requestingRepository.save(request);
                user.getFriends().add(candidate);
                userRepositori.save(user);
                return "redirect:/profile/friends";
            }
            else if(request.getRoleRequesting().equals(RoleRequesting.UNSUBSCRIBE)){
                request.setRoleRequesting(RoleRequesting.CANCEL);
                requestingRepository.save(request);
                return "redirect:/profile/friends";
            }
        }
        Requesting request = requestingService.getNewRequest(user, candidate).orElseThrow(IllegalArgumentException::new);
        request.setRoleRequesting(RoleRequesting.TAKE);
        requestingRepository.save(request);
        user.getFriends().add(candidate);
        userRepositori.save(user);
        StringBuffer url = httpServletRequest.getRequestURL();
        return "redirect:/profile/friends";
    }

    @GetMapping("/{id-candidate}/cancelRequest")
    public String postCancelRequest(@PathVariable(name = "id-candidate") Long idCandidate, Authentication authentication, HttpServletRequest httpServletRequest){
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        User candidate = userRepositori.findOneById(idCandidate).orElseThrow(IllegalArgumentException::new);
        Requesting request = requestingService.getNewRequest(user, candidate).orElseThrow(IllegalArgumentException::new);
        request.setRoleRequesting(RoleRequesting.CANCEL);
        requestingRepository.save(request);
        String url = httpServletRequest.getRequestURI();
        return "redirect:/profile/friends";
    }

    @GetMapping("/{id-candidate}/removeFromFriends")
    public String postRemoveFromFriends(@PathVariable(name = "id-candidate") Long idCandidate, Authentication authentication, HttpServletRequest httpServletRequest){
        System.out.println(httpServletRequest.getRequestURL());
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        User candidate = userRepositori.findOneById(idCandidate).orElseThrow(IllegalArgumentException::new);
        if(requestingService.getConfirmedRequest(user, candidate).isPresent()) {
            Requesting request = (requestingService.getConfirmedRequest(user, candidate)).get();
            request.setRoleRequesting(RoleRequesting.CANCEL);
            requestingRepository.save(request);
            user.getFriends().remove(candidate);
            userRepositori.save(user);
            return "redirect:/profile/friends";
        }
        else if (requestingService.getConfirmedRequest(candidate, user).isPresent()){
            Requesting request = (requestingService.getConfirmedRequest(candidate, user)).get();
            request.setRoleRequesting(RoleRequesting.CANCEL);
            requestingRepository.save(request);
            request.setInputUser(user);
            request.setOutputUser(candidate);
            candidate.getFriends().remove(user);
            userRepositori.save(candidate);
        }
        return "redirect:/profile/friends";
    }

    @GetMapping("/{id-candidate}/unsubscribe")
    public String postUnsubscribe(@PathVariable(name = "id-candidate") Long idCandidate, Authentication authentication, HttpServletRequest httpServletRequest){
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        User candidate = userRepositori.findOneById(idCandidate).orElseThrow(IllegalArgumentException::new);
        //user и candidate поменены местами
        if(requestingService.getSubscribeRequest(candidate, user).isPresent()) return "redirect:/profile/friends";
        Requesting request = requestingService.getOutputRequest(user, candidate).orElseThrow(IllegalArgumentException::new);
        if(request.getRoleRequesting().equals(RoleRequesting.NEW)){
            requestingRepository.delete(request);
        }
        else {
            request.setRoleRequesting(RoleRequesting.UNSUBSCRIBE);
            requestingRepository.save(request);
        }
        return "redirect:/profile/friends";
    }

    @PostMapping("/addPost")
    public String postAddPost(@RequestParam("inputText") String inputText, @RequestParam("idPost_hidden") Long idPost,
                              Authentication authentication){
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        if(postRepository.findOneById(idPost).isPresent()) {
            LocalDateTime now = LocalDateTime.now();
            Post post = postRepository.findOne(idPost);
            post.setContent(inputText);
            post.setTime(now);
            post.setTimeString(now.format(DateTimeFormatter.ofPattern("dd MMM uuuu в HH:mm")));
            postRepository.save(post);
        }
        else {
            LocalDateTime now = LocalDateTime.now();
            Post post = Post.builder()
                    .content(inputText)
                    .idUserTo(user.getId())
                    .time(now)
                    .timeString(now.format(DateTimeFormatter.ofPattern("dd MMM uuuu в HH:mm")))
                    .build();
            post.setOwnerPost(user);
            postRepository.save(post);
        }
        return "redirect:/profile";
    }

    @GetMapping("/deletePost/{id-post}")
    public String getDeletePost(@PathVariable("id-post") Long id){
        postRepository.delete(id);
        return "redirect:/profile";
    }

    @PostMapping("/users/{id-candidate}/addPost")
    public String postCandidateAddPost(@PathVariable("id-candidate") Long idCandidate,@RequestParam("idPost_hidden") Long idPost,
                                       @RequestParam("inputText") String inputText, Authentication authentication){
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        if(postRepository.findOneById(idPost).isPresent()){
            LocalDateTime now = LocalDateTime.now();
            Post post = postRepository.findOne(idPost);
            post.setContent(inputText);
            post.setTime(now);
            post.setTimeString(now.format(DateTimeFormatter.ofPattern("dd MMM uuuu в HH:mm")));
            postRepository.save(post);
        }
        else {
            LocalDateTime now = LocalDateTime.now();
            Post post = Post.builder()
                    .content(inputText)
                    .idUserTo(idCandidate)
                    .time(now)
                    .timeString(now.format(DateTimeFormatter.ofPattern("dd MMM uuuu в HH:mm")))
                    .build();
            post.setOwnerPost(user);
            postRepository.save(post);
        }
        return "redirect:/users/"+ idCandidate;
    }

    @GetMapping("/users/{id-candidate}/deletePost/{id-post}")
    public String getCandidateDeletePost(@PathVariable("id-candidate") Long idCandidate,@PathVariable("id-post") Long idPost){
        postRepository.delete(idPost);
        return "redirect:/users/"+ idCandidate;
    }

    @GetMapping("/profile/requests/input")
    public String getProfileRequestsInput(Authentication authentication, ModelMap modelMap){
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user", userDto);
        List<UserDto> usersDto = requestingService.getInputUsersDtoFromRequests(user);
        modelMap.addAttribute("users", usersDto);
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);
        return "requestsInput";
    }

    @GetMapping("/profile/requests/output")
    public String getProfileRequestsOutput(Authentication authentication, ModelMap modelMap){
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user", userDto);
        List<UserDto> usersDto = requestingService.getOutputUsersDtoFromRequests(user);
        modelMap.addAttribute("users", usersDto);
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);
        return "requestsOutput";
    }

    @GetMapping("/profile/requests/new")
    public String getProfileRequestsNew(Authentication authentication, ModelMap modelMap){
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user", userDto);
        List<UserDto> usersDto = requestingService.getNewUsersDtoFromRequests(user);
        modelMap.addAttribute("users", usersDto);
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if( newRequestings != null) modelMap.addAttribute("newRequestings" , newRequestings);
        return "requestsNew";
    }

}
