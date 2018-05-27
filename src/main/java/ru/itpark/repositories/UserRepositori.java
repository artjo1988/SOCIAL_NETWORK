package ru.itpark.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.itpark.models.User;

import javax.jws.soap.SOAPBinding;
import java.util.Optional;


public interface UserRepositori extends JpaRepository<User, Long>{
    Optional<User> findOneByLogin(String login);
}
