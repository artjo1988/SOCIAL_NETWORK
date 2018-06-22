package ru.itpark.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.models.Requesting;
import ru.itpark.models.RoleRequesting;
import ru.itpark.models.User;

import java.util.List;
import java.util.Optional;

@Transactional
public interface RequestingRepository extends JpaRepository<Requesting, Long> {
    Optional<List<Requesting>> findAllByInputUserAndRoleRequesting(User user, RoleRequesting roleRequesting);
    Optional<List<Requesting>> findAllByOutputUserAndRoleRequesting(User user, RoleRequesting roleRequesting);
    Optional<Requesting> findOneByInputUserAndOutputUser(User user, User candidate);
//    Optional<List<Requesting>> findAllByInputUserAndRoleRequestingOrRoleRequesting(User user, RoleRequesting one, RoleRequesting two);
//    Optional<List<Requesting>> findAllByOutputUserAndRoleRequestingOrRoleRequesting(User user, RoleRequesting one, RoleRequesting two);
    Optional<Requesting> findOneByInputUserAndOutputUserAndRoleRequesting(User inputUser, User outputUser, RoleRequesting roleRequesting);
    Optional<Requesting> findOneByInputUserAndOutputUserAndRoleRequestingOrRoleRequesting(User inputUser, User outputUser, RoleRequesting roleRequestingOne, RoleRequesting roleRequestingTwo);
}
