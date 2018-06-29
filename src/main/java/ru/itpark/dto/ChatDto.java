package ru.itpark.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.models.Chat;
import ru.itpark.models.Message;
import ru.itpark.models.User;
import ru.itpark.repositories.UserRepositori;
import ru.itpark.service.ChatService;
import ru.itpark.service.UserService;

import javax.jws.soap.SOAPBinding;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class ChatDto {

    @Autowired
    private  ChatService chatService;

    private Long id;

    private String name;

    private List<Message> messages;

    private User user;

    private User candidate;

    private String urlAva;

    private int countNewMessage;

    public ChatDto dtoChatFromChat(Chat chat, User user){
        User userTemp = null;
        User candidateTemp = null;
        List<User> users = chat.getParticipants();
        for(int i = 0; i < 2; i++){
            if (user.getId() == users.get(i).getId()) userTemp = user;
            else {
                candidateTemp = users.get(i);
            }
        }
        String urlAva = UserDto.dtoUserFromUser(candidateTemp).getAvatarUrl();
        return ChatDto.builder()
                .id(chat.getId())
                .name(candidateTemp.getFirstName() + " " + candidateTemp.getLastName())
                .messages(chat.getMessages())
                .user(userTemp)
                .candidate(candidateTemp)
                .urlAva(urlAva)
                .countNewMessage(chatService.getCountNewMessages(chat))
                .build();
    }
}
