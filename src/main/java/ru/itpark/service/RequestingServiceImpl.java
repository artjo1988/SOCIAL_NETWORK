package ru.itpark.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.dto.UserDto;
import ru.itpark.models.Requesting;
import ru.itpark.models.RoleRequesting;
import ru.itpark.models.User;
import ru.itpark.repositories.RequestingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class RequestingServiceImpl implements RequestingService {

    @Autowired
    private RequestingRepository requestingRepository;

    @Override
    public String getStatus(User user, User candidate) {
        Optional<Requesting> requestOptional = findRequest(user, candidate);
        if(requestOptional.isPresent()){
            Requesting request = requestOptional.get();
            if((request.getRoleRequesting().equals(RoleRequesting.NEW) || request.getRoleRequesting().equals(RoleRequesting.CANCEL))) {
                if(request.getInputUser().getId() == candidate.getId())return "Вы подписаны";
                else if((request.getInputUser().getId() == user.getId())) return "На Вас подписан";
            }
            else if (request.getRoleRequesting().equals(RoleRequesting.TAKE)) return "У Вас в друзьях";

        }
        return "Добавить в друзья";
    }

    @Override
    public Integer getNewRequestsCount(User user) {
        Optional<List<Requesting>> inputRequests = requestingRepository.findAllByInputUserAndRoleRequesting(user, RoleRequesting.NEW);
        if(inputRequests.isPresent()) return inputRequests.get().size();
        else return null;
    }

    @Override
    public List<UserDto> getInputUsersDtoFromRequests (User user){
        Optional<List<Requesting>> requestsOptional = getInputRequests(user);
        if(requestsOptional.isPresent()){
            List<UserDto> usersDto = new ArrayList<>();
            List<Requesting> requests = requestsOptional.get();
            for (Requesting request : requests) {
                usersDto.add(UserDto.dtoUserFromUser(request.getOutputUser()));
            }
            return usersDto;
        }
        return null;
    }

    @Override
    public List<UserDto> getNewUsersDtoFromRequests(User user) {
        Optional<List<Requesting>> requestsOptional = getNewRequests(user);
        if(requestsOptional.isPresent()){
            List<UserDto> usersDto = new ArrayList<>();
            List<Requesting> requests = requestsOptional.get();
            for (Requesting request : requests) {
                usersDto.add(UserDto.dtoUserFromUser(request.getOutputUser()));
            }
            return usersDto;
        }
        return null;
    }

    @Override
    public List<UserDto> getOutputUsersDtoFromRequests(User user) {
        Optional<List<Requesting>> requestsOptional = getOutputRequests(user);
        if(requestsOptional.isPresent()){
            List<UserDto> usersDto = new ArrayList<>();
            List<Requesting> requests = requestsOptional.get();
            for (Requesting request : requests) {
                usersDto.add(UserDto.dtoUserFromUser(request.getInputUser()));
            }
            return usersDto;
        }
        return null;
    }

    @Override
    public Optional<List<Requesting>> getOutputRequests(User user) {
        Optional<List<Requesting>> requestingsOptionalOne = requestingRepository.findAllByOutputUserAndRoleRequesting(user, RoleRequesting.NEW);
        Optional<List<Requesting>> requestingsOptionalTwo = requestingRepository.findAllByOutputUserAndRoleRequesting(user, RoleRequesting.CANCEL);
        if(requestingsOptionalOne.isPresent() && requestingsOptionalTwo.isPresent()){
            List<Requesting> list = requestingsOptionalOne.get();
            list.addAll(requestingsOptionalTwo.get());
            return Optional.of(list);
        }
        else if(requestingsOptionalOne.isPresent()){
            return requestingsOptionalOne;
        }
        else if(requestingsOptionalTwo.isPresent()){
            return requestingsOptionalTwo;
        }
        else return Optional.empty();
//        return requestingRepository.findAllByOutputUserAndRoleRequestingOrRoleRequesting(user, RoleRequesting.NEW, RoleRequesting.CANCEL);
    }

    @Override
    public Optional <List<Requesting>> getInputRequests(User user) {
        Optional<List<Requesting>> requestingsOptionalOne = requestingRepository.findAllByInputUserAndRoleRequesting(user, RoleRequesting.NEW);
        Optional<List<Requesting>> requestingsOptionalTwo = requestingRepository.findAllByInputUserAndRoleRequesting(user, RoleRequesting.CANCEL);
        if(requestingsOptionalOne.isPresent() && requestingsOptionalTwo.isPresent()){
            List<Requesting> list = requestingsOptionalOne.get();
            list.addAll(requestingsOptionalTwo.get());
            return Optional.of(list);
        }
        else if(requestingsOptionalOne.isPresent()){
            return requestingsOptionalOne;
        }
        else if(requestingsOptionalTwo.isPresent()){
            return requestingsOptionalTwo;
        }
        else return Optional.empty();
// return requestingRepository.findAllByInputUserAndRoleRequestingOrRoleRequesting(user, RoleRequesting.NEW, RoleRequesting.CANCEL);
    }

    @Override
    public Optional<Requesting> findRequest(User user, User candidate) {
        if(requestingRepository.findOneByInputUserAndOutputUser(user, candidate).isPresent())return requestingRepository.findOneByInputUserAndOutputUser(user, candidate);
        else if(requestingRepository.findOneByInputUserAndOutputUser(candidate, user).isPresent()) return requestingRepository.findOneByInputUserAndOutputUser(candidate, user);
        else return Optional.empty();
    }

    @Override
    public Optional<List<Requesting>> getNewRequests(User user) {
        return requestingRepository.findAllByInputUserAndRoleRequesting(user, RoleRequesting.NEW);
    }

    @Override
    public Optional<Requesting> getNewRequest(User user, User candidate) {
        return requestingRepository.findOneByInputUserAndOutputUserAndRoleRequesting(user, candidate, RoleRequesting.NEW);
    }

    @Override
    public Optional<Requesting> getCancelRequest(User user, User candidate) {
        return requestingRepository.findOneByInputUserAndOutputUserAndRoleRequesting(user, candidate, RoleRequesting.CANCEL);
    }

    @Override
    public Optional<Requesting> getConfirmedRequest(User user, User candidate) {
        return requestingRepository.findOneByInputUserAndOutputUserAndRoleRequesting(user, candidate, RoleRequesting.TAKE);
    }

    @Override
    public Optional<Requesting> getSubscribeRequest(User user, User candidate) {
        return requestingRepository.findOneByInputUserAndOutputUserAndRoleRequesting(user, candidate, RoleRequesting.UNSUBSCRIBE);
    }

    @Override
    public Optional<Requesting> getOutputRequest(User user, User candidate) {
        if(requestingRepository.findOneByInputUserAndOutputUserAndRoleRequesting(candidate, user, RoleRequesting.NEW).isPresent()) {
            return requestingRepository.findOneByInputUserAndOutputUserAndRoleRequesting(candidate, user, RoleRequesting.NEW);
        }
        if(requestingRepository.findOneByInputUserAndOutputUserAndRoleRequesting(candidate, user, RoleRequesting.CANCEL).isPresent()) {
            return requestingRepository.findOneByInputUserAndOutputUserAndRoleRequesting(candidate, user, RoleRequesting.CANCEL);
        }
        return Optional.empty();
    }
}
