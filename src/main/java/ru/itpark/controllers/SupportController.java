package ru.itpark.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itpark.dto.ChatDto;
import ru.itpark.dto.MessageDto;
import ru.itpark.dto.UserDto;
import ru.itpark.forms.MessageForm;
import ru.itpark.models.*;
import ru.itpark.repositories.ChatRepository;
import ru.itpark.repositories.PostRepository;
import ru.itpark.repositories.RequestingRepository;
import ru.itpark.repositories.UserRepositori;
import ru.itpark.security.details.UserDetailsImpl;
import ru.itpark.service.ChatService;
import ru.itpark.service.PostService;
import ru.itpark.service.RequestingService;
import ru.itpark.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
    private RequestingService requestingService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageDto messageDto;

    @Autowired
    private ChatDto chatDtoBean;

    @GetMapping("/")
    public String getIndexPage(Authentication authentication) {
        if (authentication == null) return "index";
        else return "redirect:/profile";
    }

    @PostMapping("/checkLogin")
    public ResponseEntity<Object> postCheckLogin(@RequestParam(name = "login") String login) {
        Optional<User> optional = userRepositori.findOneByLogin(login);
        if (optional.isPresent()) {
            return ResponseEntity.ok(new Mess("true"));
        }
        return ResponseEntity.ok(new Mess("false"));
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<Object> postCheckEmail(@RequestParam(name = "eMail") String eMail) {
        Optional<User> optional = userRepositori.findOneByEMail(eMail);
        if (optional.isPresent()) {
            return ResponseEntity.ok(new Mess("true"));
        }
        return ResponseEntity.ok(new Mess("false"));
    }

    @GetMapping("/{id-candidate}/sendRequest")
    public String postSendRequst(@PathVariable("id-candidate") Long idCandidate, Authentication authentication, HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getParameter("url");
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        User candidate = userRepositori.findOneById(idCandidate).orElseThrow(IllegalArgumentException::new);
        //Замена местами user и candidate
        if (requestingService.getNewRequest(candidate, user).isPresent()) return "redirect:" + url;
        if (requestingService.findRequest(user, candidate).isPresent()) {
            Requesting request = requestingService.findRequest(user, candidate).get();
            if (request.getRoleRequesting().equals(RoleRequesting.UNSUBSCRIBE)) {
                request.setRoleRequesting(RoleRequesting.CANCEL);
                requestingRepository.save(request);
                return "redirect:" + url;
            } else if (request.getRoleRequesting().equals(RoleRequesting.CANCEL)) {
                return "redirect:" + url;
            } else if (request.getRoleRequesting().equals(RoleRequesting.TAKE)) {
                return "redirect:" + url;
            } else if (request.getRoleRequesting().equals(RoleRequesting.NEW)) {
                return "redirect:" + url;
            }
        }
        Requesting requesting = Requesting.builder()
                .inputUser(candidate)
                .outputUser(user)
                .roleRequesting(RoleRequesting.NEW)
                .build();
        requestingRepository.save(requesting);
        return "redirect:" + url;
    }

    @GetMapping("/{id-candidate}/confirmRequest")
    public String postConfirmRequst(@PathVariable(name = "id-candidate") Long idCandidate, Authentication authentication, HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getParameter("url");
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        User candidate = userRepositori.findOneById(idCandidate).orElseThrow(IllegalArgumentException::new);
        if (requestingService.getConfirmedRequest(user, candidate).isPresent()) return "redirect:" + url;
        if (requestingService.findRequest(user, candidate).isPresent()) {
            Requesting request = requestingService.findRequest(user, candidate).get();
            if (request.getRoleRequesting().equals(RoleRequesting.CANCEL)) {
                request.setRoleRequesting(RoleRequesting.TAKE);
                requestingRepository.save(request);
                user.getFriends().add(candidate);
                userRepositori.save(user);
                return "redirect:" + url;
            } else if (request.getRoleRequesting().equals(RoleRequesting.UNSUBSCRIBE)) {
                request.setRoleRequesting(RoleRequesting.CANCEL);
                requestingRepository.save(request);
                return "redirect:" + url;
            }
        }
        Requesting request = requestingService.getNewRequest(user, candidate).orElseThrow(IllegalArgumentException::new);
        request.setRoleRequesting(RoleRequesting.TAKE);
        requestingRepository.save(request);
        user.getFriends().add(candidate);
        userRepositori.save(user);
        return "redirect:" + url;
    }

    @GetMapping("/{id-candidate}/cancelRequest")
    public String postCancelRequest(@PathVariable(name = "id-candidate") Long idCandidate, Authentication authentication, HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getParameter("url");
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        User candidate = userRepositori.findOneById(idCandidate).orElseThrow(IllegalArgumentException::new);
        if (requestingService.getCancelRequest(user, candidate).isPresent()) return "redirect:" + url;
        Requesting request = requestingService.getNewRequest(user, candidate).orElseThrow(IllegalArgumentException::new);
        request.setRoleRequesting(RoleRequesting.CANCEL);
        requestingRepository.save(request);
        return "redirect:" + url;
    }

    @GetMapping("/{id-candidate}/removeFromFriends")
    public String postRemoveFromFriends(@PathVariable(name = "id-candidate") Long idCandidate, Authentication authentication, HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getParameter("url");
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        User candidate = userRepositori.findOneById(idCandidate).orElseThrow(IllegalArgumentException::new);
        if (requestingService.getConfirmedRequest(user, candidate).isPresent()) {
            Requesting request = (requestingService.getConfirmedRequest(user, candidate)).get();
            request.setRoleRequesting(RoleRequesting.CANCEL);
            requestingRepository.save(request);
            user.getFriends().remove(candidate);
            userRepositori.save(user);
            return "redirect:" + url;
        } else if (requestingService.getConfirmedRequest(candidate, user).isPresent()) {
            Requesting request = (requestingService.getConfirmedRequest(candidate, user)).get();
            request.setRoleRequesting(RoleRequesting.CANCEL);
            requestingRepository.save(request);
            request.setInputUser(user);
            request.setOutputUser(candidate);
            candidate.getFriends().remove(user);
            userRepositori.save(candidate);
        }
        return "redirect:" + url;
    }

    @GetMapping("/{id-candidate}/unsubscribe")
    public String postUnsubscribe(@PathVariable(name = "id-candidate") Long idCandidate, Authentication authentication, HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getParameter("url");
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        User candidate = userRepositori.findOneById(idCandidate).orElseThrow(IllegalArgumentException::new);
        //user и candidate поменены местами
        if (requestingService.getSubscribeRequest(candidate, user).isPresent() || requestingService.getSubscribeRequest(user, candidate).isPresent()
                || !requestingService.findRequest(user, candidate).isPresent()) return "redirect:" + url;
        Requesting request = requestingService.getOutputRequest(user, candidate).orElseThrow(IllegalArgumentException::new);
        if (request.getRoleRequesting().equals(RoleRequesting.NEW)) {
            requestingRepository.delete(request);
        } else {
            request.setRoleRequesting(RoleRequesting.UNSUBSCRIBE);
            requestingRepository.save(request);
        }
        return "redirect:" + url;
    }

    @PostMapping("/addPost")
    public String postAddPost(@RequestParam("inputText") String inputText, @RequestParam("idPost_hidden") Long idPost,
                              Authentication authentication) {
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        if (postRepository.findOneById(idPost).isPresent()) {
            LocalDateTime now = LocalDateTime.now();
            Post post = postRepository.findOne(idPost);
            post.setContent(inputText);
            post.setTime(now);
            post.setTimeString(now.format(DateTimeFormatter.ofPattern("dd MMM uuuu в HH:mm")));
            postRepository.save(post);
        } else {
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
    public String getDeletePost(@PathVariable("id-post") Long id) {
        postRepository.delete(id);
        return "redirect:/profile";
    }

    @PostMapping("/users/{id-candidate}/addPost")
    public String postCandidateAddPost(@PathVariable("id-candidate") Long idCandidate, @RequestParam("idPost_hidden") Long idPost,
                                       @RequestParam("inputText") String inputText, Authentication authentication) {
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        if (postRepository.findOneById(idPost).isPresent()) {
            LocalDateTime now = LocalDateTime.now();
            Post post = postRepository.findOne(idPost);
            post.setContent(inputText);
            post.setTime(now);
            post.setTimeString(now.format(DateTimeFormatter.ofPattern("dd MMM uuuu в HH:mm")));
            postRepository.save(post);
        } else {
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
        return "redirect:/users/" + idCandidate;
    }

    @GetMapping("/users/{id-candidate}/deletePost/{id-post}")
    public String getCandidateDeletePost(@PathVariable("id-candidate") Long idCandidate, @PathVariable("id-post") Long idPost) {
        postRepository.delete(idPost);
        return "redirect:/users/" + idCandidate;
    }

    @GetMapping("/profile/requests/input")
    public String getProfileRequestsInput(Authentication authentication, ModelMap modelMap) {
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user", userDto);
        Optional<List<UserDto>> usersDto = requestingService.getInputUsersDtoFromRequests(user);
        if (usersDto.isPresent()) modelMap.addAttribute("users", usersDto.get());
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if (newRequestings != null) modelMap.addAttribute("newRequestings", newRequestings);
        if (chatService.getCountNewMessages(user.getChats()) != 0)
            modelMap.addAttribute("newMessages", chatService.getCountNewMessages(user.getChats()));
        return "requestsInput";
    }

    @GetMapping("/profile/requests/output")
    public String getProfileRequestsOutput(Authentication authentication, ModelMap modelMap) {
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user", userDto);
        Optional<List<UserDto>> usersDto = requestingService.getOutputUsersDtoFromRequests(user);
        if (usersDto.isPresent()) modelMap.addAttribute("users", usersDto.get());
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if (newRequestings != null) modelMap.addAttribute("newRequestings", newRequestings);
        if (chatService.getCountNewMessages(user.getChats()) != 0)
            modelMap.addAttribute("newMessages", chatService.getCountNewMessages(user.getChats()));
        return "requestsOutput";
    }

    @GetMapping("/profile/requests/new")
    public String getProfileRequestsNew(Authentication authentication, ModelMap modelMap) {
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user", userDto);
        Optional<List<UserDto>> usersDto = requestingService.getNewUsersDtoFromRequests(user);
        if (usersDto.isPresent()) modelMap.addAttribute("users", usersDto.get());
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if (newRequestings != null) modelMap.addAttribute("newRequestings", newRequestings);
        if (chatService.getCountNewMessages(user.getChats()) != 0)
            modelMap.addAttribute("newMessages", chatService.getCountNewMessages(user.getChats()));
        return "requestsNew";
    }

    @GetMapping("/users/{id-candidate}/requests/input")
    public String getCandidateRequestInput(Authentication authentication, @PathVariable("id-candidate") Long idCandidate,
                                           ModelMap modelMap) {
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        User candidate = userRepositori.findOneById(idCandidate).orElseThrow(IllegalArgumentException::new);
        UserDto candidateDto = userDto.dtoUserFromUser(candidate);
        modelMap.addAttribute("user", userDto);
        modelMap.addAttribute("candidate", candidateDto);
        Optional<List<UserDto>> usersDto = requestingService.getInputUsersDtoFromRequests(candidate);
        if (usersDto.isPresent()) {
            for (UserDto usr : usersDto.get()) {
                if (usr.getId() != userDto.getId()) {
                    usr.setStatus(requestingService.getStatus(user, userRepositori.findOne(usr.getId())));
                }
                if (usr.getId() == userDto.getId()) {
                    usr.setCondition("true");
                }
            }
            modelMap.addAttribute("users", usersDto.get());
        } else modelMap.addAttribute("message", new Mess("Пока нет подписчиков"));
        Integer newRequestings = requestingService.getNewRequestsCount(user);
        if (newRequestings != null) modelMap.addAttribute("newRequestings", newRequestings);
        if (chatService.getCountNewMessages(user.getChats()) != 0)
            modelMap.addAttribute("newMessages", chatService.getCountNewMessages(user.getChats()));
        return "requestsInputCandidate";
    }

//    @PostMapping("/getStatus")
//    public ResponseEntity<Object> postGetStatus(@RequestParam("idUser") Long idUser, @RequestParam("") Long idCandidate) {
//        String message = requestingService.getStatus(userRepositori.findOne(idUser), userRepositori.findOne(idCandidate));
//        return ResponseEntity.ok(new Mess(message));
//    }

    @GetMapping("/del")
    public String getDeleted(Authentication authentication) {
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        user.setRole(Role.ADMIN);
        userRepositori.save(user);
        return "redirect:/profile";
    }

    @GetMapping("/recover")
    public String getRecover(Authentication authentication) {
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        user.setRole(Role.USER);
        userRepositori.save(user);
        return "redirect:/profile";
    }

}
