package ru.itpark.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.models.Requesting;
import ru.itpark.models.User;

import java.util.Optional;

@Transactional
public interface RequestingRepository extends JpaRepository<Requesting, Long> {
    Optional<Requesting> findOneByCandidate(User candidate);
}
