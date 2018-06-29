package ru.itpark.service;


import org.springframework.security.core.Authentication;
import ru.itpark.forms.MessageForm;
import ru.itpark.models.Chat;
import ru.itpark.models.Message;
import ru.itpark.models.User;

import java.util.List;
import java.util.Optional;

public interface ChatService {
    Optional<Chat> findChat(Long idChat);
    Optional<Chat> isExist(Authentication authentication, Long idCandidate);
    Optional<Chat> findChat(User user, User candidate);
    Integer getCountNewMessages(Chat chat);
    Integer getCountNewMessages(List<Chat> chats);
    void saveAndDeliverMessage(Authentication authentication, Long idChat, MessageForm messageForm);
    Optional<List<Message>> getSortMessagesFromChat(Chat chat);
}
