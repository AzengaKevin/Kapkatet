package com.propscout.kapkatet.controller.web.admin;

import com.propscout.kapkatet.model.User;
import com.propscout.kapkatet.service.ClerkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClerkController {

    @Autowired
    private ClerkService clerkService;

    @Value("${app.name}")
    private String title;


    @GetMapping(value = {"/admin/clerks"})
    public String index(CsrfToken csrfToken, Model model) {

        addCsrf(csrfToken, model);

        List<User> clerks = clerkService.getAllClerks();

        model.addAttribute("users", clerks);

        model.addAttribute("title", title);

        model.addAttribute("page", "Clerks");

        model.addAttribute("USERS_REGISTERED", clerks.size() > 0);

        return "/admin/clerks/index";
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
