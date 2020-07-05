package com.propscout.kapkatet.service;

import com.propscout.kapkatet.model.User;
import com.propscout.kapkatet.model.UserDetailsImpl;
import com.propscout.kapkatet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * @param username the passed username / login identifier
     * @return UserDetail created from the user instance found from the database
     * @throws UsernameNotFoundException if the username sent matches nothing in the database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Get the user from Current Database Connection
        Optional<User> maybeUser = userRepository.findByUsername(username);

        //Throw an error if the user is null
        maybeUser.orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found in the database", username)));

        return new UserDetailsImpl(maybeUser.get());
    }
}
