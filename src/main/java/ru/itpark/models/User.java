package ru.itpark.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_sn")
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    @Column(name = "hash_password")
    private String hasPassword;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "data_birthday")
    private LocalDate dataBirthday;
    @Column(name = "city")
    private String city;
    @Column(name = "e_mail")
    private String eMail;
    @Enumerated (value = EnumType.STRING)
    private State state;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String avatar;
}
