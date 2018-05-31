package ru.itpark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itpark.forms.UserForm;
import ru.itpark.models.Role;
import ru.itpark.models.State;
import ru.itpark.models.User;
import ru.itpark.repositories.UserRepositori;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositori userRepositori;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerNewUser(UserForm userForm) {
        String hashPassword = passwordEncoder.encode(userForm.getPassword());
        userRepositori.save(User.builder()
                .login(userForm.getLogin())
                .hasPassword(hashPassword)
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
//                .dataBirthday(userForm.getData_birthday())
                .city(userForm.getCity())
                .eMail(userForm.getEMail())
                .state(State.ACTIVE)
                .role(Role.USER)
                .avatar(null)
                .build()
        );
    }

    @Override
    public List<User> getAllUsers() {
        return userRepositori.findAll();
    }
}
