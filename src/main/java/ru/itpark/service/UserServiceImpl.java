package ru.itpark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.forms.UserForm;
import ru.itpark.models.FileInfo;
import ru.itpark.models.Role;
import ru.itpark.models.State;
import ru.itpark.models.User;
import ru.itpark.repositories.FileInfoRepository;
import ru.itpark.repositories.UserRepositori;
import ru.itpark.security.details.UserDetailsImpl;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositori userRepositori;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Override
    public void registerNewUser(UserForm userForm) {
        String hashPassword = passwordEncoder.encode(userForm.getPassword());

        User newUser = User.builder()
                .login(userForm.getLogin())
                .hashPassword(hashPassword)
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .dataBirthday(LocalDate.parse(userForm.getDataBirthday()))
                .city(userForm.getCity())
                .eMail(userForm.getEMail())
                .state(State.ACTIVE)
                .role(Role.USER)
                .build();
        userRepositori.save(newUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepositori.findAll();
    }

    @Override
    public void changeData(UserForm userForm, Authentication authentication) {
        User user = userRepositori.findOne(getUserInfo(authentication).getId());
        user.setLogin(userForm.getLogin());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setCity(userForm.getCity());
        user.setDataBirthday(LocalDate.parse(userForm.getDataBirthday()));
        userRepositori.save(user);
    }

    @Override
    public User getUserInfo(Authentication authentication) {
        UserDetailsImpl details = (UserDetailsImpl)authentication.getPrincipal();
        return details.getUser();
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        return userRepositori.findOneById(id).orElseThrow(IllegalArgumentException::new);
    }
}
