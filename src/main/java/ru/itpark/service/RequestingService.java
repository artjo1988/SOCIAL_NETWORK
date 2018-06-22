package ru.itpark.service;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.itpark.dto.UserDto;
import ru.itpark.models.Requesting;
import ru.itpark.models.User;

import java.util.List;
import java.util.Optional;

public interface RequestingService {
//    Boolean getStatus(User outputUser, User inputUser);
    Integer getNewRequestsCount(User user);
    Optional <List<Requesting>> getInputRequests(User user);
    Optional <List<Requesting>> getOutputRequests(User user);
    Optional<List<Requesting>> getNewRequests(User user);
    Optional<Requesting> getNewRequest(User user, User candidate);
    Optional<Requesting> findRequest(User user, User candidate);
    Optional<Requesting> getConfirmedRequest(User user, User candidate);
    Optional<Requesting> getCancelRequest(User user, User candidate);
    Optional<Requesting> getOutputRequest(User user, User candidate);
    Optional<Requesting> getSubscribeRequest(User user, User candidate);
    List<UserDto> getInputUsersDtoFromRequests (User user);
    List<UserDto> getNewUsersDtoFromRequests (User user);
    List<UserDto> getOutputUsersDtoFromRequests (User user);

}
