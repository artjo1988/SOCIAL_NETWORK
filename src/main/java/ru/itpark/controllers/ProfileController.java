package ru.itpark.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itpark.dto.UserDto;
import ru.itpark.models.User;
import ru.itpark.repositories.UserRepositori;
import ru.itpark.security.details.UserDetailsImpl;
import ru.itpark.service.UserServiceImpl;

@Controller
public class ProfileController {

    @Autowired
    UserRepositori userRepositori;

    @GetMapping("/profile")
    public String getProfilePage(ModelMap modelMap, Authentication authentication){
        if(authentication == null){
            return "redirect:/login";
        }
        UserDetailsImpl details = (UserDetailsImpl)authentication.getPrincipal();
        UserDto userDto = UserDto.dtoUserFromUser(userRepositori.findOneById(details.getUser().getId()));
        modelMap.addAttribute("user",userDto);
        return "profile";

    }
}
