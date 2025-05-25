package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String showAdminDashboard(Model model) {
        // Add any model attributes needed for the dashboard
        return "admin"; // This corresponds to your admin.html template
    }

    @GetMapping("/services/add")
    public String showAddServiceForm(Model model) {
        // Logic to show form for adding new service
        return "add-service";
    }

    @GetMapping("/services/search")
    public String showSearchServiceForm(Model model) {
        // Logic to show search service form
        return "search-service";
    }

    @GetMapping("/services/list")
    public String listAllServices(Model model) {
        // Logic to retrieve and list all services
        return "list-services";
    }

    @GetMapping("/responses")
    public String viewCustomerResponses(Model model) {
        // Logic to retrieve customer responses
        return "customer-responses";
    }

    @GetMapping("/appointments")
    public String viewAppointments(Model model) {
        // Logic to retrieve appointments
        return "appointments";
    }

    @GetMapping("/staff")
    public String manageStaff(Model model) {
        // Logic for staff management
        return "manage-staff";
    }
}