package ru.itpark.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itpark.forms.PasswordForm;
import ru.itpark.models.User;
import ru.itpark.service.EmailService;
import ru.itpark.service.ProfileService;
import ru.itpark.service.UserService;

@Controller
public class ProfileController {

    //    @Value("${register.setSubject}");
//    private String setSubject;
//    @Value("${register.message}");
//    private String registerMessage;
//
//    @Value("${changePassword.setSubject}");
//    private String setSubject;
//    @Value("${changePassword.message}");
//    private String registerMessage;
//
//    @Value("${changeEmail.setSubject}");
//    private String setSubject;
//    @Value("${changeEmail.message}");
//    private String registerMessage;

    @Autowired
    ProfileService profileService;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @GetMapping("/changePassword")
    public String getChangePassword(){
        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String postChangePassword(@ModelAttribute PasswordForm passwordForm, Authentication authentication,
                                    ModelMap modelMap){
       if(profileService.correctPassword(passwordForm, authentication)){
           User user = userService.getUserInfo(authentication);
           String eMail = user.getEMail();
           profileService.changePassword(passwordForm, authentication);
           emailService.sendMail("Изменение пароля прошло успешно!", "Изменение пароля!", eMail);
           return "redirect:/profile";
       }
       modelMap.addAttribute("message", "Некорректно введены данные");
       modelMap.addAttribute("error", true);
       return "changePassword";
    }
}
