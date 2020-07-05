package com.propscout.kapkatet.controller.web;

import com.propscout.kapkatet.model.User;
import com.propscout.kapkatet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @Autowired
    private UserService userService;

    @Value("${app.name}")
    private String title;

    @GetMapping(value = {"/", "/index"})
    public String index(CsrfToken csrfToken, Model model) throws Exception {
        model.addAttribute("title", title);
        model.addAttribute("page", "Welcome");
        addCurrentUserDetailsToAuthenticatedUsersRequestModel(csrfToken, model);
        return "pages/index";
    }

    @GetMapping(value = {"/about"})
    public String about(CsrfToken csrfToken, Model model) throws Exception {
        model.addAttribute("title", title);
        model.addAttribute("page", "About");
        addCurrentUserDetailsToAuthenticatedUsersRequestModel(csrfToken, model);
        return "pages/about";
    }

    @GetMapping(value = {"/contact"})
    public String contact(CsrfToken csrfToken, Model model) throws Exception {
        model.addAttribute("title", title);
        model.addAttribute("page", "Contact");
        addCurrentUserDetailsToAuthenticatedUsersRequestModel(csrfToken, model);

        return "pages/contact";
    }

    private void addCurrentUserDetailsToAuthenticatedUsersRequestModel(CsrfToken csrfToken, Model model) throws Exception {

        model.addAttribute("_csrf", csrfToken.getToken());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User currentUser = userService.findByUsername(authentication.getName()).orElseThrow(Exception::new);

            //Check the current user role to manage the dashboard url to be shown

            if (authentication.getAuthorities().stream().findFirst().isPresent()) {
                String role = authentication.getAuthorities().stream().findFirst().get().toString();
                System.out.println(role);

                switch (role) {
                    case "Admin":
                        model.addAttribute("admin", true);
                        break;
                    case "Clerk":
                        model.addAttribute("clerk", true);
                        break;
                    default:
                        model.addAttribute("default", true);
                }
            }

            model.addAttribute("user", currentUser);

            model.addAttribute("LOGGED_IN", true);
        }

    }
}
