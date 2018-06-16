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
import ru.itpark.service.UserService;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/")
    public String getIndexPage(Authentication authentication) {
        if(authentication == null)return "index";
        else return "redirect:/profile";
    }

    @PostMapping("/checkLogin")
    public boolean postCheckLogin(@RequestParam(name = "login") String login){
        Optional<User> optional = userRepositori.findOneByLogin(login);
        if(optional.isPresent()) return true;
        return false;
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<Object>  postCheckEmail(@RequestParam(name = "eMail") String eMail){
        Optional<User> optional = userRepositori.findOneByEMail(eMail);
        if(optional.isPresent()) ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id-candidate}/sendRequst")
    public void postSendRequst(@PathVariable("id-candidate") Long id, Authentication authentication){
        User person = userService.getUserInfo(authentication);
        User candidate = userRepositori.findOneById(id).orElseThrow(IllegalArgumentException::new);
        Requesting requesting = Requesting.builder()
                .person(person)
                .candidate(candidate)
                .roleRequesting(RoleRequesting.NEW)
                .build();
        requestingRepository.save(requesting);
    }

    @PostMapping("/confirmRequest")
    public void postConfirmRequst(){

    }

    @PostMapping("/cancelRequest")
    public void postCancelRequest(){

    }

    @PostMapping("/addPost")
    public String postAddPost(@RequestParam("inputText") String inputText, ModelMap modelMap,
                              Authentication authentication, HttpServletRequest request){
        User user = userService.getUserInfo(authentication);
        UserDto userDto = UserDto.dtoUserFromUser(user);
        modelMap.addAttribute("user",userDto);
        Post post = Post.builder()
                .content(inputText)
                .build();
        post.setOwnerPost(user);
        postRepository.save(post);
        modelMap.addAttribute("posts", user.getPosts());
        String url = request.getRequestURI();
        return "redirect:/profile";
    }

    @GetMapping("/deletePost/{id-post}")
    public String getDeletePost(@PathVariable("id-post") Long id){
        postRepository.delete(id);
        return "redirect:/profile";
    }

    @PostMapping("/users/{id-candidate}/addPost")
    public String postCandidateAddPost(@PathVariable("id-candidate") Long idCandidate,@RequestParam("inputText") String inputText,
                              ModelMap modelMap, Authentication authentication, HttpServletRequest request){
        User userPerson = userService.getUserInfo(authentication);
        UserDto personDto = UserDto.dtoUserFromUser(userPerson);
        modelMap.addAttribute("user", personDto);
        User userCandidate = userService.getUserById(idCandidate);
        UserDto candidateDto = UserDto.dtoUserFromUser(userCandidate);
        modelMap.addAttribute("candidate", candidateDto);
        List<Post> reverseList = postService.reverseList(candidateDto.getPosts());
        modelMap.addAttribute("posts", reverseList);
        SupportInfo info = SupportInfo.builder()
                .friends(candidateDto.getFriends().size())
                .subscribers(candidateDto.getInputRequestings().size())
                .posts(candidateDto.getPosts().size())
                .chats(candidateDto.getChats().size())
                .build();
        modelMap.addAttribute("info", info);
        Post post = Post.builder()
                .content(inputText)
                .build();
        post.setOwnerPost(userPerson);
        postRepository.save(post);
        modelMap.addAttribute("posts", personDto.getPosts());
        return "redirect:/users/"+ idCandidate;
    }

    @GetMapping("/users/{id-candidate}/deletePost/{id-post}")
    public String getCandidateDeletePost(@PathVariable("id-post") Long id){
        postRepository.delete(id);
        return "redirect:/profile";
    }

}
