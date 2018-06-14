package ru.itpark.models;


import lombok.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "chat")
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "user_chat",
            joinColumns = @JoinColumn(name = "chat"),
            inverseJoinColumns = @JoinColumn(name = "user_sn"))
    private List<User> participant;

}
