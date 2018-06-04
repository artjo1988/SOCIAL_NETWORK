package ru.itpark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.forms.UserForm;
import ru.itpark.models.Role;
import ru.itpark.models.State;
import ru.itpark.models.User;
import ru.itpark.repositories.UserRepositori;
import ru.itpark.security.details.UserDetailsImpl;
import java.util.List;

@Transactional
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
                .hashPassword(hashPassword)
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
//                .dataBirthday(userForm.getData_birthday())
                .city(userForm.getCity())
                .eMail(userForm.getEMail())
                .state(State.ACTIVE)
                .role(Role.USER)
                .build()
        );
    }

    @Override
    public List<User> getAllUsers() {
        return userRepositori.findAll();
    }

    @Override
    public void changeData(UserForm userForm, Authentication authentication) {
        User user = getUserInfo(authentication);
        Long id = user.getId();
        String login = userForm.getLogin();
        String firstName = userForm.getFirstName();
        String lastName = userForm.getLastName();
        String city = userForm.getCity();
        userRepositori.updateUserByIdFromForm(login, firstName, lastName, city, id);
    }

    @Override
    public User getUserInfo(Authentication authentication) {
        UserDetailsImpl details = (UserDetailsImpl)authentication.getPrincipal();
        return details.getUser();
    }

}
