package com.propscout.kapkatet.controller.web.admin;

import com.propscout.kapkatet.model.Center;
import com.propscout.kapkatet.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@PreAuthorize("hasRole('Admin')")
public class CenterController {

    @Value("${app.name}")
    private String title;

    @Autowired
    private CenterService centerService;

    @GetMapping(value = {"admin/centers"})
    public String index(CsrfToken csrfToken, Model model) {
        addCsrf(csrfToken, model);

        List<Center> centers = centerService.findAll();

        long centersCount = centerService.count();

        model.addAttribute("title", title);

        model.addAttribute("page", "Centers");

        model.addAttribute("centers", centers);

        model.addAttribute("CENTERS_ADDED", centersCount > 0);

        return "admin/centers/index";

    }

    @PostMapping(value = {"admin/centers"})
    public String store(Model model, @ModelAttribute("center") Center center) {

        try {

            centerService.save(center);

            return "redirect:/admin/centers";

        } catch (Exception e) {
            e.printStackTrace();

            return "redirect:/admin/centers/create";
        }

    }

    @GetMapping(value = {"admin/centers/create"})
    public String create(CsrfToken csrfToken, Model model) {
        addCsrf(csrfToken, model);

        model.addAttribute("title", title);

        model.addAttribute("page", "Add Center");

        return "admin/centers/create";

    }

    @GetMapping(value = {"admin/centers/{centerId}"})
    public String show(CsrfToken csrfToken, Model model, @PathVariable int centerId) {
        addCsrf(csrfToken, model);

        model.addAttribute("title", title);

        model.addAttribute("page", "Show Center");

        return "admin/centers/show";

    }


    @GetMapping(value = {"admin/centers/{centerId}/edit"})
    public String edit(CsrfToken csrfToken, Model model, @PathVariable int centerId) {

        //Getting a single center from the db via the repository and the service

        Center center = centerService.findById(centerId);

        addCsrf(csrfToken, model);

        model.addAttribute("title", title);

        model.addAttribute("page", "Edit Center");

        model.addAttribute("center", center);

        return "admin/centers/edit";

    }

    @PostMapping(value = {"admin/centers/{centerId}/edit"})
    public String update(Model model, @PathVariable int centerId, @ModelAttribute("center") Center center) {

        center.setId(centerId);

        //Try persisting the changes, in case of a fail return back
        try {

            centerService.update(center);

            return "redirect:/admin/centers";

        } catch (Exception e) {
            e.printStackTrace();

            return String.format("redirect:/admin/centers/%d/edit", centerId);

        }

    }

    @PostMapping(value = {"admin/centers/{centerId}"})
    public String delete(Model model, @PathVariable int centerId) {

        try {

            centerService.deleteByIt(centerId);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "redirect:/admin/centers";
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
