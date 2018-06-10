package ru.itpark.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.models.User;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Optional;


@Transactional
public interface UserRepositori extends JpaRepository<User, Long>{
    Optional<User> findOneByLogin(String login);
    Optional<User> findOneByEMail(String eMail);
    Optional<User> findOneById(Long id);
    List<User> findUsersByFirstNameContainsOrLastNameContains(String str, String str2);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user_sn SET login = ?1, first_name =?2, " +
            "last_name = ?3, city = ?4 WHERE id = ?5")
    void updateUserByIdFromForm(String login, String firstNmae, String Last, String city, Long id);

}
