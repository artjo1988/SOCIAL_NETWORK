package ru.itpark.models;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupportInfo {
    private String status;
    private int friends;
    private int subscribers;
    private int posts;
    private int chats;
}
