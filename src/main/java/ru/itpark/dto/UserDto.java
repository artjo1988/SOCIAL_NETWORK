package ru.itpark.dto;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.models.FileInfo;
import ru.itpark.models.Requesting;
import ru.itpark.models.User;

import java.time.LocalDate;
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
//    private LocalDate data_birthday;
    private String city;
    private String avatarUrl;
    private List<User> friends;
    private List<Requesting> outputRequestings;
    private List<Requesting> inputRequestings;


    public static UserDto dtoUserFromUser(User user){
        String avatarUrlUser = null;
        if(user.getAvatarFileInfo() != null) avatarUrlUser = "/files/" + user.getAvatarFileInfo().getStorageName();

        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
//                .data_birthday(user.getDataBirthday())
                .city(user.getCity())
                .avatarUrl(avatarUrlUser)
                .friends(user.getFriends())
                .outputRequestings(user.getOutputRequestings())
                .inputRequestings(user.getInputRequestings())
                .build();
    }
}
