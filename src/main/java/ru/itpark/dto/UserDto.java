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
    private String login;
    private String first_name;
    private String last_name;
    private LocalDate data_birthday;
    private String city;

    public static UserDto dtoUserFromDetails(User user){
        return UserDto.builder()
                .login(user.getLogin())
                .first_name(user.getFirstName())
                .last_name(user.getLastName())
                .data_birthday(user.getDataBirthday())
                .city(user.getCity())
                .build();
    }
}
