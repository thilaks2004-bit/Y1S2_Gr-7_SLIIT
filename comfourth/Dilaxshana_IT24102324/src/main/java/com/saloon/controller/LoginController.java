package com.saloon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.saloon.model.User;
import com.saloon.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/profile")
    public String profile(@ModelAttribute User user, Model model, HttpSession session) {
        User existingUser = userService.getUserByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("currentUserEmail", user.getEmail());
            model.addAttribute("user", existingUser);
            return "profile";
        }
        model.addAttribute("error", "Invalid email or password");
        model.addAttribute("user", user);
        return "login";
    }
}