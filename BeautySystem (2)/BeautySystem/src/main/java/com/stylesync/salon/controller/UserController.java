package com.stylesync.salon.controller;

import com.stylesync.salon.model.RegularUser;
import com.stylesync.salon.model.User;
import com.stylesync.salon.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegularUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") RegularUser user,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Validation errors: {}", result.getAllErrors());
            return "register";
        }
        try {
            User existingUser = userService.findUserByUsername(user.getUsername());
            if (existingUser != null) {
                model.addAttribute("error", "Username already exists");
                return "register";
            }
            userService.registerUser(user);
            model.addAttribute("message", "Registration successful! Please log in.");
            return "redirect:/users/login";
        } catch (IOException e) {
            logger.error("Failed to register user: {}", e.getMessage(), e);
            model.addAttribute("error", "Failed to register. Please try again.");
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new RegularUser());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") RegularUser user, Model model) {
        try {
            User existingUser = userService.findUserByUsername(user.getUsername());
            if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
                model.addAttribute("user", existingUser);
                return "redirect:/users/profile/" + existingUser.getId();
            }
            model.addAttribute("error", "Invalid username or password");
            return "login";
        } catch (IOException e) {
            logger.error("Failed to login user: {}", e.getMessage(), e);
            model.addAttribute("error", "An error occurred during login.");
            return "login";
        }
    }

    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable Long id, Model model) {
        try {
            User user = userService.findUserById(id);
            if (user == null) {
                model.addAttribute("error", "User not found");
                return "redirect:/users/login";
            }
            model.addAttribute("user", user);
            return "profile";
        } catch (IOException e) {
            logger.error("Failed to load profile for ID {}: {}", id, e.getMessage(), e);
            model.addAttribute("error", "Failed to load profile.");
            return "redirect:/users/login";
        }
    }

    @PostMapping("/profile/{id}")
    public String updateProfile(@PathVariable Long id, @Valid @ModelAttribute("user") RegularUser user,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Validation errors during profile update: {}", result.getAllErrors());
            return "profile";
        }
        user.setId(id);
        try {
            userService.updateUser(user);
            model.addAttribute("message", "Profile updated successfully");
        } catch (IOException e) {
            logger.error("Failed to update profile for ID {}: {}", id, e.getMessage(), e);
            model.addAttribute("error", "Failed to update profile. Please try again.");
        }
        return "profile";
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        try {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            return "user-list";
        } catch (IOException e) {
            logger.error("Failed to load user list: {}", e.getMessage(), e);
            model.addAttribute("error", "Failed to load user list.");
            return "user-list";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            logger.info("Deleted user with ID: {}", id);
        } catch (IOException e) {
            logger.error("Failed to delete user with ID {}: {}", id, e.getMessage(), e);
        }
        return "redirect:/users/list";
    }
}