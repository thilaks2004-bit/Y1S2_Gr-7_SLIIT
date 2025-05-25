package com.saloon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saloon.model.User;
import com.saloon.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EditProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/edit-profile")
    public String showEditProfileForm(Model model, HttpSession session) {
        String currentUserEmail = (String) session.getAttribute("currentUserEmail");
        if (currentUserEmail == null) {
            model.addAttribute("error", "Please log in to edit your profile");
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(currentUserEmail);
        if (user == null) {
            model.addAttribute("error", "User not found");
            session.removeAttribute("currentUserEmail");
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "edit-profile";
    }

    @PostMapping("/edit-profile")
    public String updateProfile(@ModelAttribute("user") User user,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        String currentUserEmail = (String) session.getAttribute("currentUserEmail");
        if (currentUserEmail == null) {
            redirectAttributes.addFlashAttribute("error", "Please log in to edit your profile");
            return "redirect:/login";
        }

        User currentUser = userService.getUserByEmail(currentUserEmail);
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/login";
        }

        // Password confirmation
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            model.addAttribute("user", currentUser); // retain old data
            return "edit-profile";
        }

        // Email uniqueness check (only if changed)
        if (!user.getEmail().equals(currentUser.getEmail())) {
            User existingUser = userService.getUserByEmail(user.getEmail());
            if (existingUser != null) {
                model.addAttribute("error", "Email is already in use");
                model.addAttribute("user", currentUser); // retain old data
                return "edit-profile";
            }
        }

        // Force set values that should not be altered by form
        currentUser.setEmail(user.getEmail());
        currentUser.setContactNo(user.getContactNo());
        currentUser.setAddress(user.getAddress());
        currentUser.setRole(user.getRole());
        currentUser.setPassword(user.getPassword());
        try {
            userService.updateUser(currentUser);
            redirectAttributes.addFlashAttribute("message", "Profile updated successfully");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update profile due to server error");
            model.addAttribute("user", currentUser);
            return "edit-profile";
        }
    }
}
