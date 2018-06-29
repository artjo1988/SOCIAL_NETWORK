package ru.itpark.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.itpark.models.Chat;
import ru.itpark.models.User;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findById(Long id);
    Optional<Chat> findChatByParticipantsContains(User user);
    Optional<Chat> findChatByParticipantsContainsAndParticipantsContains(User user, User candidate);
}
