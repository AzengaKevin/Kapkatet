package com.propscout.kapkatet.service;

import com.propscout.kapkatet.model.User;
import com.propscout.kapkatet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClerkService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllClerks() {
        return userRepository.findByRoles("Clerk");
    }
}
