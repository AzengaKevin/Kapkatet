package com.propscout.kapkatet.service;

import com.propscout.kapkatet.model.User;
import com.propscout.kapkatet.repository.RoleRepository;
import com.propscout.kapkatet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User register(User user) throws Exception {

        //Check whether the username is already taken
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new Exception(String.format("Username %s already taken", user.getUsername()));
        }

        //Check whether the email is already taken
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception(String.format("Email %s already taken", user.getEmail()));
        }

        //Check whether the email is already taken
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception(String.format("Phone %s already taken", user.getPhone()));
        }

        if (user.getId() != null && userRepository.findById(user.getId()).isPresent()) {
            throw new Exception("Try to save an existing user");
        }

        //Set default user for th registering user
        user.addRole(roleRepository.findById(4L).orElse(null));

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
