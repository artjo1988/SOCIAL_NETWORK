package ru.itpark.forms;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForm {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String dataBirthday;
    private String city;
    private String eMail;
}
