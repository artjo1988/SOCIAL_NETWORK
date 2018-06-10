package ru.itpark.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itpark.dto.UserDto;
import ru.itpark.models.Requesting;
import ru.itpark.models.RoleRequesting;
import ru.itpark.models.User;
import ru.itpark.repositories.RequestingRepository;
import ru.itpark.repositories.UserRepositori;
import ru.itpark.service.UserService;

import java.util.Optional;

@Controller
public class SupportController {

    @Autowired
    private UserRepositori userRepositori;

    @Autowired
    private RequestingRepository requestingRepository;

    @Autowired
    private UserService userService;

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
}
