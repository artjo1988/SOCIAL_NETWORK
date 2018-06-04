package ru.itpark.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itpark.dto.UserDto;
import ru.itpark.forms.UserForm;
import ru.itpark.models.User;
import ru.itpark.security.details.UserDetailsImpl;
import ru.itpark.service.UserService;


@Controller
public class EditController {

    @Autowired
    private UserService userService;

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
}
