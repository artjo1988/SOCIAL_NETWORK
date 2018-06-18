package ru.itpark.service;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.itpark.models.Requesting;
import ru.itpark.models.User;

import java.util.Optional;

public interface RequestingService {
    Boolean getStatus(User outputUser, User inputUser);
    Integer getNewRequesting(User user);
}
