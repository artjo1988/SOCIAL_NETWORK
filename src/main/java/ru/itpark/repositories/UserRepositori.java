package ru.itpark.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itpark.models.User;
import java.util.Optional;


public interface UserRepositori extends JpaRepository<User, Long>{
    Optional<User> findOneByLogin(String login);
}
