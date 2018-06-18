package ru.itpark.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.models.Requesting;
import ru.itpark.models.User;

import java.util.List;
import java.util.Optional;

@Transactional
public interface RequestingRepository extends JpaRepository<Requesting, Long> {
    Optional<Requesting> findByOutputUserAndInputUser(User outputUser, User inputUser);
    Optional<List<Requesting>> findAllByInputUser(User user);
}
