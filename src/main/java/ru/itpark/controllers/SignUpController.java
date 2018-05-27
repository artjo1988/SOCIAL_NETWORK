package ru.itpark.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.itpark.forms.UserForm;
import ru.itpark.service.UserService;


@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @GetMapping("/signUp")
    public String getSignUpPage(ModelMap modelMap){
        modelMap.addAttribute("usersFromServer", userService.getAllUsers());
        return "signUp";
    }


    @PostMapping("/signUp")
    public String postSignUpPage(@ModelAttribute UserForm userForm){
        userService.registerNewUser(userForm);
        return "redirect:/login";
    }
}
