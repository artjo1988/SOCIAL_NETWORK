package ru.itpark.service;

import org.springframework.security.core.Authentication;
import ru.itpark.forms.UserForm;
import ru.itpark.models.User;
import java.util.List;


public interface UserService {
    void registerNewUser(UserForm userForm);
    List<User> getAllUsers();
    void changeData(UserForm userForm, Authentication authentication);

}
