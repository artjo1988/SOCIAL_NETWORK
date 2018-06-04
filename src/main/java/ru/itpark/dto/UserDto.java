package ru.itpark.dto;

import lombok.*;
import ru.itpark.models.FileInfo;
import ru.itpark.models.User;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
//    private LocalDate data_birthday;
    private String city;
    private String avatarUrl;


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
                .build();
    }
}
