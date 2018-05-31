package ru.itpark.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForm {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
//    private LocalDate data_birthday;
    private String city;
    private String eMail;
}
