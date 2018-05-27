package ru.itpark.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String getLoginPage(HttpServletRequest request, ModelMap modelMap) {
        if (request.getParameterMap().containsKey("error")) {
            modelMap.addAttribute("error", true);
        }
        return "login";
    }
}
