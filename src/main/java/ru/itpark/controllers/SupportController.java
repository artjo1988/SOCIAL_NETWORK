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

    @GetMapping("/users/{id-candidate}/sendRequest")
    public String postSendRequst(@PathVariable("id-candidate") Long idCandidate, Authentication authentication){
        User user = userService.getUserInfo(authentication);
        User candidate = userRepositori.findOneById(idCandidate).orElseThrow(IllegalArgumentException::new);
        Requesting requesting  = Requesting.builder()
                .inputUser(candidate)
                .outputUser(user)
                .roleRequesting(RoleRequesting.NEW)
                .build();
        requestingRepository.save(requesting);
        return "redirect:/users/" + idCandidate;
    }

    @PostMapping("users/{id-candidate}/confirmRequest")
    public void postConfirmRequst(){

    }

    @PostMapping("users/{id-candidate}/cancelRequest")
    public void postCancelRequest(){

    }

    @PostMapping("users/{id-candidate}/removeFromFriends")
    public void RemoveFromFriends(){

    }

    @PostMapping("/addPost")
    public String postAddPost(@RequestParam("inputText") String inputText, @RequestParam("idPost_hidden") Long idPost,
                              Authentication authentication){
        User user = userService.getUserInfo(authentication);
        if(postRepository.findOneById(idPost).isPresent()) {
            Post post = postRepository.findOne(idPost);
            post.setContent(inputText);
            postRepository.save(post);
        }
        else {
            Post post = Post.builder()
                    .content(inputText)
                    .idUserTo(user.getId())
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
        User user = userService.getUserInfo(authentication);
        if(postRepository.findOneById(idPost).isPresent()){
            Post post = postRepository.findOne(idPost);
            post.setContent(inputText);
            postRepository.save(post);
        }
        else {
            Post post = Post.builder()
                    .content(inputText)
                    .idUserTo(idCandidate)
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

}
