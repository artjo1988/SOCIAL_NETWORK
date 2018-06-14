package ru.itpark.models;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", length = 765)
    private String content;

//    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "owner_post")
    private User ownerPost;

}
