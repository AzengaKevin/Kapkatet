package com.propscout.kapkatet.controller.web;

import com.propscout.kapkatet.model.User;
import com.propscout.kapkatet.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Value("${app.name}")
    private String title;

    @GetMapping(value = {"/login"})
    public String login(CsrfToken csrfToken, Model model) {
        model.addAttribute("title", title);

        //Check if use is authenticate
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //Redirect to home page if authenticated
        if (!(authentication instanceof AnonymousAuthenticationToken)) return "redirect:/";

        model.addAttribute("_csrf", csrfToken.getToken());

        return "auth/login";
    }

    @GetMapping(value = {"/register"})
    public String showRegisterForm(CsrfToken csrfToken, Model model) {

        model.addAttribute("title", title);
        model.addAttribute("_csrf", csrfToken.getToken());

        return "auth/register";
    }

    @PostMapping(value = {"/register"})
    public String register(Model model, @ModelAttribute("user") User user) {

        System.out.println("User " + user);

        try {
            User registeredUser = userService.register(user);

            if (registeredUser != null) {
                model.addAttribute("message", "User registered successfully");

                return "redirect:/login";
            }

        } catch (Exception e) {

            logger.error(e.getLocalizedMessage(), e);

            model.addAttribute("error", e.getLocalizedMessage());

            return "redirect:/register";
        }
        model.addAttribute("error", "Unknown error occurred");

        return "redirect:/register";
    }


    @GetMapping(value = {"/forgot-password"})
    public String forgotPassword(Model model) {

        model.addAttribute("title", title);

        return "auth/forgot-password";
    }
}
