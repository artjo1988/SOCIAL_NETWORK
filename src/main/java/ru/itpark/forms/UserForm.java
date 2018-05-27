package ru.itpark.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForm {
    private Long id;
    private String login;
    private String password;
    private String first_name;
    private String last_name;
    private LocalDate data_birthday;
    private String city;
    private String e_mail;
}
