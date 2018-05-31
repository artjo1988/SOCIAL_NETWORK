package ru.itpark.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @Column(name = "login")
    private String login;
    @Column(name = "hashPassword")
    private String hasPassword;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
//    @Column(name = "data_birthday")
//    private LocalDate dataBirthday;
    @Column(name = "city")
    private String city;
    @Column(name = "eMail")
    private String eMail;
    @Enumerated (value = EnumType.STRING)
    private State state;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Column(name = "avatar")
    private String avatar;
}
