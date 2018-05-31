package ru.itpark.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itpark.models.User;

import java.time.LocalDate;

@Data
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


    public static UserDto dtoUserFromDetails(User user){
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
//                .data_birthday(user.getDataBirthday())
                .city(user.getCity())
                .build();
    }
}
