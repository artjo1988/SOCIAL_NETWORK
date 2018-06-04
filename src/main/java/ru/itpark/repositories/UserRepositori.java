package ru.itpark.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.itpark.models.User;
import java.util.Optional;


public interface UserRepositori extends JpaRepository<User, Long>{
    Optional<User> findOneByLogin(String login);
    User findOneById(Long id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user_sn SET login = ?1, first_name =?2, " +
            "last_name = ?3, city = ?4 WHERE id = ?5")
    void updateUserByIdFromForm(String login, String firstNmae, String Last, String city, Long id);
}
