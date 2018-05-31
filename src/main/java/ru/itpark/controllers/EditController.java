package ru.itpark.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itpark.dto.UserDto;
import ru.itpark.forms.UserForm;
import ru.itpark.security.details.UserDetailsImpl;
import ru.itpark.service.UserService;


@Controller
public class EditController {

    @Autowired
    private UserService userService;

    @GetMapping("/edit")
    public String getSignUpPage(ModelMap modelMap, Authentication authentication){
        UserDetailsImpl details = (UserDetailsImpl)authentication.getPrincipal();
        UserDto userDto = UserDto.dtoUserFromDetails(details.getUser());
        modelMap.addAttribute("user",userDto);
        return "edit";
    }


    @PostMapping("/edit")
    public String postSignUpPage(@ModelAttribute UserForm userForm){
        userService.registerNewUser(userForm);
        return "redirect:/login";
    }
}
