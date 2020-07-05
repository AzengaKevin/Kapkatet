package com.propscout.kapkatet.controller.web;

import com.propscout.kapkatet.model.User;
import com.propscout.kapkatet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Value("${app.name}")
    private String title;

    @Autowired
    private UserService userService;

    @GetMapping(value = {"dashboard"})
    public String user(CsrfToken csrfToken, Model model) throws Exception {
        model.addAttribute("title", title);
        model.addAttribute("page", "Dashboard");
        addCsrf(csrfToken, model);
        addCurrentUserDetailsToAuthenticatedUsersRequestModel(model);

        return "user/dashboard";
    }


    @PreAuthorize("hasRole('Clerk')")
    @GetMapping(value = {"clerk/dashboard"})
    public String clerk(CsrfToken csrfToken, Model model) throws Exception {
        model.addAttribute("title", title);
        model.addAttribute("page", "Clerk Dashboard");
        addCsrf(csrfToken, model);
        addCurrentUserDetailsToAuthenticatedUsersRequestModel(model);


        return "clerk/dashboard";
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = {"admin/dashboard"})
    public String admin(CsrfToken csrfToken, Model model) throws Exception {
        model.addAttribute("title", title);
        model.addAttribute("page", "Admin Dashboard");
        addCsrf(csrfToken, model);
        addCurrentUserDetailsToAuthenticatedUsersRequestModel(model);

        return "admin/dashboard";
    }

    private void addCsrf(CsrfToken csrfToken, Model model) {
        model.addAttribute("_csrf", csrfToken.getToken());
    }


    private void addCurrentUserDetailsToAuthenticatedUsersRequestModel(Model model) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User currentUser = userService.findByUsername(authentication.getName()).orElseThrow(Exception::new);

            //Check the current user role to manage the dashboard url to be shown

            if (authentication.getAuthorities().stream().findFirst().isPresent()) {

                String role = authentication.getAuthorities().stream().findFirst().get().toString();
                System.out.println(role);

            }

            model.addAttribute("user", currentUser);
        }

    }
}
