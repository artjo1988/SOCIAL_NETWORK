package ru.itpark.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.itpark.models.Chat;
import ru.itpark.models.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<List<Message>> findMessagesByChatOrderById(Chat chat);
}
