package ru.itpark.forms;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailForm {
    private String oldEmail;
    private String newEmail;
    private String reNewEmail;
}
