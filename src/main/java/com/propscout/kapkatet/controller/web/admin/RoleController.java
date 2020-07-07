package com.propscout.kapkatet.controller.web.admin;

import com.propscout.kapkatet.model.Role;
import com.propscout.kapkatet.repository.RoleRepository;
import com.propscout.kapkatet.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Value("${app.name}")
    private String title;

    @GetMapping(value = {"/admin/roles"})
    public String index(CsrfToken csrfToken, Model model) {

        List<Role> roles = roleService.getAllRoles();

        model.addAttribute("title", title);

        model.addAttribute("page", "Roles");

        addCsrf(csrfToken, model);

        model.addAttribute("roles", roles);

        model.addAttribute("ROLES_AVAILABLE", roleService.count() > 0);


        return "/admin/roles/index";
    }

    @GetMapping(value = {"/admin/roles/create"})
    public String create(CsrfToken csrfToken, Model model) {

        addCsrf(csrfToken, model);

        model.addAttribute("title", title);

        model.addAttribute("page", "Add Role");

        return "/admin/roles/create";
    }

    @GetMapping(value = {"/admin/roles/{roleId}/edit"})
    public String edit(CsrfToken csrfToken, Model model, @PathVariable int roleId) {

        try {

            Role role = roleService.findById(roleId);

            addCsrf(csrfToken, model);

            model.addAttribute("title", title);

            model.addAttribute("page", "Edit Role");

            model.addAttribute("role", role);

            return "/admin/roles/edit";


        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/admin/roles";

        }

    }

    @PostMapping(value = {"/admin/roles"})
    public String store(@ModelAttribute("role") Role role) {

        System.out.println(role);

        try {

            Role createdRole = roleService.createRole(role);

            if (createdRole == null)

                return "redirect:/admin/roles/create";

            else
                return "redirect:/admin/roles";


        } catch (Exception exception) {

            exception.printStackTrace();

            return "redirect:/admin/roles/create";

        }
    }

    @PostMapping(value = {"/admin/roles/{roleId}/edit"})
    public String update(@PathVariable int roleId, @ModelAttribute("roles") Role role) {

        role.setId(roleId);

        System.out.println(role);

        try {

            roleService.updateRole(role);

            return "redirect:/admin/roles";

        } catch (Exception e) {
            e.printStackTrace();
            return String.format("redirect:/admin/roles/%d/edit", roleId);
        }
    }

    @PostMapping(value = {"/admin/roles/{roleId}"})
    public String delete(@PathVariable int roleId) {


        System.out.println("Role id: " + roleId);

        try {

            roleService.deleteRoleById(roleId);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "redirect:/admin/roles";

    }

    private void addCsrf(CsrfToken csrfToken, Model model) {
        model.addAttribute("_csrf", csrfToken.getToken());
    }

}
