package ru.itpark.dto;


import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.itpark.models.Message;
import ru.itpark.models.RoleMessage;
import ru.itpark.models.User;
import ru.itpark.repositories.MessageRepository;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Component
public class MessageDto {

    @Autowired
    private MessageRepository messageRepository;

    private String ava;

    private String from;

    private String message;

    public  List<MessageDto> dtoMessagesFromMessages(List<Message> messages, UserDto user){
        List<MessageDto> messagesDto = new ArrayList<>();
        for (Message message : messages){
            if(message.getRoleMessage().equals(RoleMessage.UNREAD)) message.setRoleMessage(RoleMessage.READ);
//            if(message.getAuthor().getId() == user.getId()){
//                MessageDto messageDto = MessageDto.builder()
//                        .ava(user.getAvatarUrl())
//                        .from("Вы")
//                        .message(message.getText())
//                        .build();
//                messagesDto.add(messageDto);
//            }
//            else {
                MessageDto messageDto = MessageDto.builder()
                        .ava(user.getAvatarUrl())
                        .from(message.getAuthor().getFirstName())
                        .message(message.getText())
                        .build();
                messagesDto.add(messageDto);
//            }
            messageRepository.save(message);
        }
        return messagesDto;
    }

    public MessageDto dtoMessageFromMessage(Message message, UserDto user){
        MessageDto messageDto;
        if(message.getRoleMessage().equals(RoleMessage.UNREAD)) message.setRoleMessage(RoleMessage.READ);
//        if(message.getAuthor().getId() == user.getId()){
//            messageDto = MessageDto.builder()
//                .ava(user.getAvatarUrl())
//                .from("Вы")
//                .message(message.getText())
//                .build();
//        }
//        else {
            messageDto = MessageDto.builder()
                .ava(user.getAvatarUrl())
                .from(message.getAuthor().getFirstName())
                .message(message.getText())
                .build();
//        }
        messageRepository.save(message);
        return messageDto;
    }
}
