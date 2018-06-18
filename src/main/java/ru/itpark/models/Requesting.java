package ru.itpark.models;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Transactional
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "requesting")
@Entity
public class Requesting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "input_user")
    private User inputUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "output_user")
    private User outputUser;

    @Enumerated(EnumType.STRING)
    private RoleRequesting roleRequesting;

}
