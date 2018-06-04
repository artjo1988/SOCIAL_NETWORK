package ru.itpark.forms;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordForm {
    private String oldPassword;
    private String newPassword;
    private String reNewPassword;
}
