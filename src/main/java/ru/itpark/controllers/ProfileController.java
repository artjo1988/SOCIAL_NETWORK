package ru.itpark.controllers;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itpark.dto.UserDto;
import ru.itpark.forms.EmailForm;
import ru.itpark.forms.PasswordForm;
import ru.itpark.forms.UserForm;
import ru.itpark.models.User;
import ru.itpark.repositories.UserRepositori;
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
    private UserRepositori userRepositori;

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

    @GetMapping("/changeEmail")
    public String getChangeEmail(){
        return "changeEmail";
    }

    @PostMapping("/changeEmail")
    public String getChangeEmail(@ModelAttribute EmailForm emailForm, Authentication authentication,
                                 ModelMap modelMap){
        if(profileService.correctEmail(emailForm, authentication)) {
            User user = userService.getUserInfo(authentication);
            profileService.changeEmail(emailForm, authentication);
            User refUser = userRepositori.findOne(user.getId());
            String eMail = refUser.getEMail();
            emailService.sendMail("Изменение почты прошло успешно!", "Изменение почты!", eMail);
            return "redirect:/profile";
        }
        modelMap.addAttribute("message", "Некорректно введены данные");
        modelMap.addAttribute("error", true);
        return "changeEmail";
    }

    @GetMapping("/recoverPassword/email")
    public String getRecoverPasswordEmail(){
        return "recoverPasswordEmail";
    }

    @PostMapping("/recoverPassword/form")
    public String getRecoverPassword(@ModelAttribute UserForm userForm, ModelMap modelMap){
        String eMail = userForm.getEMail();
        String firstName = userForm.getFirstName();
        String lastName = userForm.getLastName();
        User recoverUser = userRepositori.findOneByEMail(eMail).orElse(new User());
        if(recoverUser == null){
            modelMap.addAttribute("message", "Пользователь с таким Email не зарегистрирован");
            modelMap.addAttribute("error", true);
            return "recoverPasswordEmail";
        }
        else {
            if(firstName.equals(recoverUser.getFirstName()) &&
                    lastName.equals(recoverUser.getLastName())){
                modelMap.addAttribute("eMail", eMail);
                return "recoverPassword";
            }
            else {
                modelMap.addAttribute("message", "Некорректно введены данные");
                modelMap.addAttribute("error", true);
                return "recoverPasswordEmail";
            }
        }
    }

    @PostMapping("/recoverPassword")
    public String postReecoverPassword(@ModelAttribute PasswordForm passwordForm, @RequestParam(name = "eMail_hidden") String eMail,
                                        ModelMap modelMap){
        String newPassword = passwordForm.getNewPassword();
        String reNewPassword = passwordForm.getReNewPassword();
        if(newPassword.equals(reNewPassword)) {
            profileService.changePasswordRecover(passwordForm, eMail);
            emailService.sendMail("Изменение пароля прошло успешно!", "Изменение пароля!", eMail);
            return "redirect:/login";
        }
        else{
            modelMap.addAttribute("message", "Некорректно введены данные");
            modelMap.addAttribute("error", true);
            modelMap.addAttribute("eMail", eMail);
            return "recoverPassword";
        }
    }
}
