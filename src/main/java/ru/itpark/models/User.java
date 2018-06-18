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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "hashPassword")
    private String hashPassword;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "data_birthday")
    private String dataBirthday;

    @Column(name = "city")
    private String city;

    @Column(name = "eMail")
    private String eMail;

    @Enumerated(value = EnumType.STRING)
    private State state;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "owner")
    private FileInfo avatarFileInfo;

    @OneToMany(mappedBy = "inputUser")
    private List<Requesting> inputRequestings;

    @OneToMany(mappedBy = "outputUser")
    private List<Requesting> outputRequestings;

    @ManyToMany
    @JoinTable(name="tbl_friends",
            joinColumns=@JoinColumn(name="personId"),
            inverseJoinColumns=@JoinColumn(name="friendId")
    )
    private List<User> friends;

    @ManyToMany
    @JoinTable(name="tbl_friends",
            joinColumns=@JoinColumn(name="friendId"),
            inverseJoinColumns=@JoinColumn(name="personId")
    )
    private List<User> friendOf;

    @OneToMany(mappedBy = "ownerPost")
    private List<Post> posts;

    @ManyToMany
    @JoinTable(name = "user_chat",
            joinColumns = @JoinColumn(name = "user_sn"),
            inverseJoinColumns = @JoinColumn(name = "chat"))
    private List<Chat> chats;
}
