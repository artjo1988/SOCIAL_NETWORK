package ru.itpark.dto;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.models.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Transactional
public class UserDto {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String data_birthday;
    private String city;
    private String avatarUrl;
    private List<User> friends;
    private List<Requesting> outputRequestings;
    private List<Requesting> inputRequestings;
    private List<Post> posts;
    private List<Chat> chats;


    public static UserDto dtoUserFromUser(User user){
        String avatarUrlUser = null;
        if(user.getAvatarFileInfo() != null) avatarUrlUser = "/files/" + user.getAvatarFileInfo().getStorageName();
        else avatarUrlUser = "/img/no_avatar.jpg";

        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .data_birthday(user.getDataBirthday())
                .city(user.getCity())
                .avatarUrl(avatarUrlUser)
                .friends(user.getFriends())
                .outputRequestings(user.getOutputRequestings())
                .inputRequestings(user.getInputRequestings())
                .posts(user.getPosts())
                .chats(user.getChats())
                .build();
    }
}
