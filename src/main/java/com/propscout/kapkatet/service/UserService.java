package com.propscout.kapkatet.service;

import com.propscout.kapkatet.model.User;
import com.propscout.kapkatet.repository.RoleRepository;
import com.propscout.kapkatet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User register(User user) throws Exception {

        validateUserInputs(user);

        validateAgainstExistingUsers(user);

        //Set default user for th registering user
        user.addRole(roleRepository.findById(4L).orElse(null));

        return userRepository.save(user);
    }

    public User register(User user, long roleId) throws Exception {

        validateUserInputs(user);

        validateAgainstExistingUsers(user);

        //Set default user for th registering user
        user.addRole(roleRepository.findById(roleId).orElse(null));

        return userRepository.save(user);
    }

    public User findById(long id) {

        return userRepository.findById(id).orElse(null);

    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAllUsers() {

        return userRepository.findAll();

    }

    public void update(User user) throws Exception {

        validateUserInputs(user);

        if (!userRepository.existsById(user.getId())) {
            throw new Exception(String.format("Cannot find user with the id: %d", user.getId()));
        }

        userRepository.save(user);
    }

    public void deleteUserBiId(long id) throws Exception {

        if (!userRepository.existsById(id)) {
            throw new Exception("No user found with such id. select available user please");
        }

        userRepository.deleteById(id);
    }

    public long count() {
        return userRepository.count();
    }


    private void validateUserInputs(User user) throws Exception {

        //Validating non null fields
        if (StringUtils.isEmpty(user.getName())) throw new Exception("Name is required");
        if (StringUtils.isEmpty(user.getUsername())) throw new Exception("Username is required");
        if (StringUtils.isEmpty(user.getEmail())) throw new Exception("Email is required");
        if (StringUtils.isEmpty(user.getPhone())) throw new Exception("Phone is required");
    }

    private void validateAgainstExistingUsers(User user) throws Exception {

        //Check whether the username is already taken
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new Exception(String.format("Username %s already taken", user.getUsername()));
        }

        //Check whether the email is already taken
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception(String.format("Email %s already taken", user.getEmail()));
        }

        //Check whether the email is already taken
        if (userRepository.findByEmail(user.getPhone()).isPresent()) {
            throw new Exception(String.format("Phone %s already taken", user.getPhone()));
        }

        if (user.getId() != null && userRepository.findById(user.getId()).isPresent()) {
            throw new Exception("Try to save an existing user");
        }
    }
}
