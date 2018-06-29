package ru.itpark.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.dto.MessageDto;
import ru.itpark.dto.UserDto;
import ru.itpark.forms.MessageForm;
import ru.itpark.models.Chat;
import ru.itpark.models.Message;
import ru.itpark.models.RoleMessage;
import ru.itpark.models.User;
import ru.itpark.repositories.ChatRepository;
import ru.itpark.repositories.MessageRepository;
import ru.itpark.repositories.UserRepositori;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepositori userRepositori;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageDto messageDto;

    @Override
    public Optional<Chat> findChat(Long idChat) {
        if(chatRepository.findById(idChat).isPresent()) return chatRepository.findById(idChat);
        else return Optional.empty();
    }

    @Override
    public Optional<Chat> isExist(Authentication authentication, Long idCandidate) {
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        User candidate = userRepositori.findOneById(idCandidate).orElseThrow(IllegalArgumentException::new);
        Optional<Chat> chatOptional= chatRepository.findChatByParticipantsContainsAndParticipantsContains(user, candidate);
        if(chatOptional.isPresent()){
            Chat chat = chatOptional.get();
            for(User usr : chat.getParticipants()){
                if(usr.getId() == candidate.getId()) return chatOptional;
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Chat> findChat(User user, User candidate) {
        return chatRepository.findChatByParticipantsContainsAndParticipantsContains(user, candidate);
    }

    @Override
    public Integer getCountNewMessages(Chat chat) {
        int count = 0;
        List<Message> messages = chat.getMessages();
        if(messages.size() != 0){
            for (Message message : messages){
                if(message.getRoleMessage().equals(RoleMessage.UNREAD)) count++;
            }
            return count;
        }
        else return 0;
    }

    @Override
    public Integer getCountNewMessages(List<Chat> chats) {
        int count = 0;
        if(chats.size() != 0){
            for(Chat chat: chats){
                count += getCountNewMessages(chat);
            }
            return count;
        }
        else return 0;
    }

    @Override
    public void saveAndDeliverMessage(Authentication authentication, Long idChat, MessageForm messageForm) {
        User user = userRepositori.findOne(userService.getUserInfo(authentication).getId());
        UserDto userDto = UserDto.dtoUserFromUser(user);
        Chat chat = chatService.findChat(idChat).orElseThrow(IllegalArgumentException::new);
        Message message = Message.builder()
                .author(user)
                .chat(chat)
                .text(messageForm.getText())
                .roleMessage(RoleMessage.READ)
                .build();
        messageRepository.save(message);
        simpMessagingTemplate.convertAndSend("/topic/chats/" + idChat, messageDto.dtoMessageFromMessage(message, userDto));
    }

    @Override
    public Optional<List<Message>> getSortMessagesFromChat(Chat chat) {
        return  messageRepository.findMessagesByChatOrderById(chat);
    }
}
