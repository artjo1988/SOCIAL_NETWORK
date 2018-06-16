package ru.itpark.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.forms.EmailForm;
import ru.itpark.forms.PasswordForm;
import ru.itpark.models.User;
import ru.itpark.repositories.UserRepositori;

@Transactional
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepositori userRepositori;

    @Override
    public void changePassword(PasswordForm passwordForm, Authentication authentication) {
        User user = userService.getUserInfo(authentication);
        String newPassword = passwordForm.getNewPassword();
        user.setHashPassword(passwordEncoder.encode(newPassword));
        userRepositori.save(user);
    }

    @Override
    public void changePasswordRecover(PasswordForm passwordForm, String eMail) {
        User user = userRepositori.findOneByEMail(eMail).orElseThrow(IllegalArgumentException::new);
        String newPassword = passwordForm.getNewPassword();
        user.setHashPassword(passwordEncoder.encode(newPassword));
        userRepositori.save(user);
    }

    @Override
    public void changeEmail(EmailForm emailForm, Authentication authentication) {
        User user = userService.getUserInfo(authentication);
        String newEmail= emailForm.getNewEmail();
        user.setEMail(newEmail);
        userRepositori.save(user);
    }

    @Override
    public boolean correctPassword(PasswordForm passwordForm, Authentication authentication) {
        User user = userService.getUserInfo(authentication);
        String hashPassword = user.getHashPassword();
        String oldPassword = passwordForm.getOldPassword();
        String newPassword = passwordForm.getNewPassword();
        String reNewPassword = passwordForm.getReNewPassword();
        if(passwordEncoder.matches(oldPassword, hashPassword)
                && newPassword.equals(reNewPassword) && !(oldPassword.equals(reNewPassword))) return true;
        return false;
    }

    @Override
    public boolean correctEmail(EmailForm emailForm, Authentication authentication) {
        User user = userService.getUserInfo(authentication);
        String eMail = user.getEMail();
        String oldEmail = emailForm.getOldEmail();
        String newEmail = emailForm.getNewEmail();
        String reNewEmail = emailForm.getReNewEmail();
        if(eMail.equals(oldEmail) && newEmail.equals(reNewEmail) && !(oldEmail.equals(reNewEmail))) return true;
        return false;
    }


}
