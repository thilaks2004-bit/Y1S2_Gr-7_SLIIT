package com.stylesync.salon.controller;

import com.stylesync.salon.model.RegularUser;
import com.stylesync.salon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showAdminLoginForm(Model model) {
        model.addAttribute("user", new RegularUser());
        return "admin-login";
    }

    @PostMapping("/login")
    public String adminLogin(@ModelAttribute("user") RegularUser user, Model model) {
        logger.info("Admin login attempt with username: {}", user.getUsername());
        try {
            if (userService.validateAdminLogin(user.getUsername(), user.getPassword())) {
                logger.info("Admin login successful for username: {}", user.getUsername());
                return "redirect:/users/list";
            } else {
                logger.warn("Admin login failed for username: {}", user.getUsername());
                model.addAttribute("error", "Invalid admin username or password");
                return "admin-login";
            }
        } catch (IOException e) {
            logger.error("Error during admin login: {}", e.getMessage(), e);
            model.addAttribute("error", "An error occurred. Please try again.");
            return "admin-login";
        }
    }
}