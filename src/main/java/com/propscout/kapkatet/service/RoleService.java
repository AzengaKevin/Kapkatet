package com.propscout.kapkatet.service;

import com.propscout.kapkatet.model.Role;
import com.propscout.kapkatet.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role findById(long id) throws Exception {

        if (!roleRepository.existsById(id)) {
            throw new Exception("Role you selected does not exist, you might wanna check that again");
        }
        return roleRepository.findById(id).orElse(null);
    }
}
