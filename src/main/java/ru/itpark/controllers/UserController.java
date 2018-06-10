package ru.itpark.controllers;

import org.hibernate.Hibernate;
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
import ru.itpark.models.Requesting;
import ru.itpark.models.User;
import ru.itpark.repositories.UserRepositori;
import ru.itpark.security.details.UserDetailsImpl;
import ru.itpark.service.EmailService;
import ru.itpark.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    UserRepositori userRepositori;

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

    @GetMapping("/profile")
    public String getProfilePage(ModelMap modelMap, Authentication authentication){
        if(authentication == null){
            return "redirect:/login";
        }
        UserDetailsImpl details = (UserDetailsImpl)authentication.getPrincipal();
        UserDto userDto = UserDto.dtoUserFromUser(userRepositori.findOne(details.getUser().getId()));
        modelMap.addAttribute("user",userDto);
        return "profile";
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

    @GetMapping("/friends")
    public String getFriendsPage(ModelMap modelMap, Authentication authentication){
//        UserDto userDto = UserDto.dtoUserFromUser(userService.getUserInfo(authentication));
//        modelMap.addAttribute("user", userDto);
        return "friends";
    }

    @GetMapping("/users")
    public String getUsersPage(ModelMap modelMap, Authentication authentication){
        UserDetailsImpl details = (UserDetailsImpl)authentication.getPrincipal();
        UserDto userDto = UserDto.dtoUserFromUser(userRepositori.findOne(details.getUser().getId()));
        modelMap.addAttribute("user",userDto);
        List<User> users = userRepositori.findAllByIdIsNot(userDto.getId());
        modelMap.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/users/find")
    public String postUsersFind(HttpServletRequest request, ModelMap modelMap,
                                Authentication authentication){
        String paramFind = request.getParameter("paramFind");
        UserDetailsImpl details = (UserDetailsImpl)authentication.getPrincipal();
        UserDto userDto = UserDto.dtoUserFromUser(userRepositori.findOne(details.getUser().getId()));
        List<User> users = userRepositori.findAllByIdIsNot(userDto.getId());
        if (paramFind == null){
            modelMap.addAttribute("user",userDto);
            modelMap.addAttribute("users", users);
            return "users";
        }
        else {

            String[] arrStr = paramFind.split("\\s+");

            if(arrStr.length==2) {
                String paramFindOne = arrStr[0];
                String paramFindTwo = arrStr[1];
                List<User> listFindOne = userRepositori.findUsersByFirstNameContainsOrLastNameContains(paramFindOne, paramFindTwo);
                List<User> listFindTwo = userRepositori.findUsersByFirstNameContainsOrLastNameContains(paramFindTwo, paramFindOne);
                for (User user : listFindOne) {
                    if (listFindTwo.contains(user)) listFindTwo.remove(user);
                }
                listFindOne.addAll(listFindTwo);
                if (listFindOne.contains(userService.getUserInfo(authentication)))
                    listFindOne.remove(userService.getUserInfo(authentication));
                modelMap.addAttribute("users", listFindOne);
                modelMap.addAttribute("user", userDto);
                return "users";
            }
            else{
                String paramFindOne = arrStr[0];
                List<User> listFindOne = userRepositori.findUsersByFirstNameContains(paramFindOne);
                List<User> listFindTwo = userRepositori.findUsersByLastNameContains(paramFindOne);
                for (User user : listFindOne) {
                    if (listFindTwo.contains(user)) listFindTwo.remove(user);
                }
                listFindOne.addAll(listFindTwo);
                if (listFindOne.contains(userService.getUserInfo(authentication)))
                    listFindOne.remove(userService.getUserInfo(authentication));
                modelMap.addAttribute("users", listFindOne);
                modelMap.addAttribute("user", userDto);
                return "users";
            }
        }
    }

    @GetMapping("/users/{id-candidate}")
    public String getCandidatePage(ModelMap modelMap, Authentication authentication,
                                @PathVariable("id-candidate") Long idCandidate){
        User userPerson = userService.getUserInfo(authentication);
        UserDto personDto = UserDto.dtoUserFromUser(userPerson);
        modelMap.addAttribute("person", personDto);
        User userCandidate = userService.getUserById(idCandidate);
        UserDto candidateDto = UserDto.dtoUserFromUser(userCandidate);
        modelMap.addAttribute("candidate", candidateDto);
        List<Requesting> requestings = userPerson.getOutputRequestings();
        Hibernate.initialize(userCandidate);
        if (requestings.contains(userCandidate))modelMap.addAttribute("status", "Вы подписаны");
        return "profileCandidate";
    }

}



