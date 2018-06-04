package ru.itpark.service;


import org.springframework.security.core.Authentication;
import ru.itpark.forms.EmailForm;
import ru.itpark.forms.PasswordForm;

public interface ProfileService {
    boolean correctPassword(PasswordForm passwordForm, Authentication authentication);
    void changePassword(PasswordForm passwordForm, Authentication authentication);
    boolean correctEmail(EmailForm emailForm, Authentication authentication);
    void changeEmail(EmailForm emailForm, Authentication authentication);
}
