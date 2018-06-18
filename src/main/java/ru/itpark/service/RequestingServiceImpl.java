package ru.itpark.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.models.Requesting;
import ru.itpark.models.User;
import ru.itpark.repositories.RequestingRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class RequestingServiceImpl implements RequestingService {

    @Autowired
    private RequestingRepository requestingRepository;

    @Override
    public Boolean getStatus(User outputUser, User inputUser) {
        Optional<Requesting> requesting = requestingRepository.findByOutputUserAndInputUser(outputUser, inputUser);
        if(requesting.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public Integer getNewRequesting(User user) {
        Optional<List<Requesting>> inputRequestings = requestingRepository.findAllByInputUser(user);
        if(inputRequestings.isPresent()) return inputRequestings.get().size();
        else return null;
    }
}
