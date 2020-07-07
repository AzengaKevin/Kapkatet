package com.propscout.kapkatet.service;

import com.propscout.kapkatet.model.Role;
import com.propscout.kapkatet.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public Role createRole(Role role) throws Exception {

        validateUserInput(role);

        if (role.getId() != null && roleRepository.existsById(role.getId())) {
            throw new Exception("A role with the same id already exists");
        }

        return roleRepository.save(role);
    }

    public void updateRole(Role role) throws Exception {

        validateUserInput(role);

        if (!roleRepository.existsById(role.getId())) {

            throw new Exception("No role with such id exists");
        }

        roleRepository.save(role);
    }

    public void deleteRoleById(long roleId) throws Exception {

        //Check if a role with  the passed id exists else throw an exception

        if (!roleRepository.existsById(roleId))
            throw new Exception("No role with such id, select another role to delete");

        roleRepository.deleteById(roleId);
    }

    public long count() {
        return roleRepository.count();
    }

    private void validateUserInput(Role role) throws Exception {

        if (StringUtils.isEmpty(role.getName())) throw new Exception("Name is required");

    }
}
