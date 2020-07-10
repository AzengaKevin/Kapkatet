package com.propscout.kapkatet.controller.web.admin;

import com.propscout.kapkatet.model.Center;
import com.propscout.kapkatet.model.ScheduleItem;
import com.propscout.kapkatet.model.User;
import com.propscout.kapkatet.service.CenterService;
import com.propscout.kapkatet.service.ScheduleService;
import com.propscout.kapkatet.service.UserService;
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
public class ScheduleController {

    @Value("${app.name}")
    private String title;

    @Autowired
    private UserService userService;

    @Autowired
    private CenterService centerService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping(value = {"/admin/clerks/{userId}/centers/assign"})
    public String create(CsrfToken csrfToken, Model model, @PathVariable long userId) {

        User currentUser = userService.findById(userId);

        List<Center> centers = centerService.findAll();

        model.addAttribute("title", title);

        model.addAttribute("page", "Assign Center");

        model.addAttribute("currentUser", currentUser);

        model.addAttribute("centers", centers);

        addCsrf(csrfToken, model);

        return "/admin/clerks/assign-center";
    }


    @GetMapping(value = {"/admin/clerks/{userId}/centers"})
    public String show(CsrfToken csrfToken, Model model, @PathVariable int userId) {

        User currentUser = userService.findById(userId);

        List<ScheduleItem> scheduleItems = scheduleService.findByUserId(userId);

        model.addAttribute("title", title);

        model.addAttribute("page", "Assign Center");

        model.addAttribute("currentUser", currentUser);

        model.addAttribute("schedule", scheduleItems);

        addCsrf(csrfToken, model);

        return "/admin/clerks/centers/index";
    }

    @PostMapping(value = {"/admin/clerks/{userId}/centers"})
    public String store(@PathVariable long userId, @ModelAttribute ScheduleItem scheduleItem) {
        scheduleItem.setUserId(userId);

        System.out.println(scheduleItem);

        try {

            scheduleService.addScheduleItem(scheduleItem);

            return String.format("redirect:/admin/clerks/%d/centers", userId);


        } catch (Exception exception) {
            exception.printStackTrace();

            return String.format("redirect:/admin/clerks/%d/centers/assign", userId);

        }

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
