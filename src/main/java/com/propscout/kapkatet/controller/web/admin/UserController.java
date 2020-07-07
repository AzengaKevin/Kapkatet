package com.propscout.kapkatet.controller.web.admin;

import com.propscout.kapkatet.model.Role;
import com.propscout.kapkatet.model.User;
import com.propscout.kapkatet.service.RoleService;
import com.propscout.kapkatet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Value("${app.name}")
    private String title;

    @GetMapping(value = {"/admin/users"})
    public String index(CsrfToken csrfToken, Model model) {

        List<User> users = userService.findAllUsers();

        long usersCount = userService.count();

        addCsrf(csrfToken, model);

        model.addAttribute("users", users);

        model.addAttribute("title", title);

        model.addAttribute("page", "Users");

        model.addAttribute("USERS_REGISTERED", usersCount > 0);

        return "admin/users/index";
    }

    @PostMapping(value = {"/admin/users"})
    public String store(@ModelAttribute("user") User user, @RequestParam("role_id") int roleId) {

        try {

            userService.register(user, roleId);

            return "redirect:/admin/users";

        } catch (Exception exception) {

            exception.printStackTrace();

            return "redirect:/admin/users/create";
        }
    }

    @GetMapping(value = {"/admin/users/create"})
    public String create(CsrfToken csrfToken, Model model) {

        //Get all the roles in the database
        List<Role> roles = roleService.getAllRoles();

        model.addAttribute("title", title);

        model.addAttribute("page", "Add User");

        model.addAttribute("roles", roles);

        addCsrf(csrfToken, model);

        return "/admin/users/create";
    }

    @GetMapping(value = {"/admin/users/{userId}/edit"})
    public String edit(CsrfToken csrfToken, Model model, @PathVariable long userId) {
        addCsrf(csrfToken, model);

        User user = userService.findById(userId);

        List<Role> roles = roleService.getAllRoles();


        model.addAttribute("title", title);

        model.addAttribute("page", "Users");

        model.addAttribute("currentUser", user);

        model.addAttribute("roles", roles);

        return "admin/users/edit";
    }

    @PostMapping(value = {"/admin/users/{userId}/edit"})
    public String update(@PathVariable long userId, @ModelAttribute("user") User user, @RequestParam("role_id") int roleId) {

        try {
            user.setId(userId);

            user.addRole(roleService.findById(roleId));

            userService.update(user);

            return "redirect:/admin/users";

        } catch (Exception e) {
            e.printStackTrace();

            return String.format("/admin/users/%dl/edit", userId);
        }
    }

    @PostMapping(value = {"/admin/users/{userId}"})
    public String deleteUser(@PathVariable long userId) {

        System.out.println("User Id: " + userId);

        try {

            userService.deleteUserBiId(userId);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "redirect:/admin/users";
    }


    /**
     * Add cross site request forgery token to the calling route
     *
     * @param csrfToken the token holder
     * @param model     the model to control the view
     */
    private void addCsrf(CsrfToken csrfToken, Model model) {
        model.addAttribute("_csrf", csrfToken.getToken());
    }
}
