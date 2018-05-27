package ru.itpark.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itpark.repositories.UserRepositori;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepositori userRepositori;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return new UserDetailsImpl(userRepositori.findOneByLogin(login).orElseThrow(IllegalArgumentException::new));
    }
}
