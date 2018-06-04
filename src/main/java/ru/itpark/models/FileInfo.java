package ru.itpark.models;

import lombok.*;

import javax.persistence.*;

@Entity()
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table (name = "avatar_sn")

public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "original_name")
    private String originalName;
    @Column(name = "storage_name")
    private String storageName;
    private Long size;
    private String type;
    private String url;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_avatar")
    private User owner;

}
