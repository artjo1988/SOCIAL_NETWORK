package ru.itpark.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
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
    private String hashPassword;
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
    @OneToOne(mappedBy = "owner")
    private FileInfo avatarFileInfo;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "friendly_roles",
            joinColumns = {@JoinColumn(name = "basic_role")},
            inverseJoinColumns = {@JoinColumn(name = "supporting_role")})
    private List<User> who_you_friend;
    @ManyToMany(cascade = CascadeType.MERGE, mappedBy = "who_you_friend")
    private List<User> friends;

}
