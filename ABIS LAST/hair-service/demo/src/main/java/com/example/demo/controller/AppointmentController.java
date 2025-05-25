package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.model.Service;
import com.example.demo.model.HairService;
import com.example.demo.model.GenericService;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.RegularBooking;
import com.example.demo.service.PremiumBooking;
import com.example.demo.service.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/book")
    public String showBookingForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("services", appointmentService.getAllServices());
        return "book-appointment";
    }

    @PostMapping("/book")
    public String bookAppointment(@ModelAttribute Appointment appointment, @RequestParam String userType) {
        Booking booking = userType.equals("Premium") ?
                new PremiumBooking(appointmentService) :
                new RegularBooking(appointmentService);
        boolean success = booking.bookAppointment(appointment);
        return success ? "redirect:/appointments/list" : "error";
    }

    @GetMapping("/reschedule/{id}")
    public String showRescheduleForm(@PathVariable String id, Model model) {
        Appointment appointment = appointmentService.getAllAppointments().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(new Appointment());
        model.addAttribute("appointment", appointment);
        model.addAttribute("services", appointmentService.getAllServices());
        return "reschedule-appointment";
    }

    @PostMapping("/reschedule/{id}")
    public String rescheduleAppointment(@PathVariable String id, @ModelAttribute Appointment appointment) {
        boolean success = appointmentService.updateAppointment(id, appointment);
        return success ? "redirect:/appointments/list" : "error";
    }

    @GetMapping("/list")
    public String listAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "appointment-list";
    }

    @PostMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable String id) {
        boolean success = appointmentService.deleteAppointment(id);
        return success ? "redirect:/appointments/list" : "error";
    }
    @GetMapping("/cancel")
    public String adminlistAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "amincancel";
    }



    @GetMapping("/")
    public String redirectToList() {
        return "redirect:/appointments/list";
    }

    @GetMapping("/home")
    public String redirectToHome() {
        return "redirect:/appointments/book";
    }
    @GetMapping("/admin")
    public String adminHome() {
        return "admin";
    }

    @GetMapping("/add-service")
    public String showAddServiceForm(Model model) {
        model.addAttribute("service", new GenericService());
        return "add-service";
    }

    @PostMapping("/add-service")
    public String addService(@ModelAttribute("service") GenericService service, @RequestParam(required = false) String hairType) {
        Service newService = service;
        if ("Hair".equals(service.getCategory()) && hairType != null && !hairType.isEmpty()) {
            newService = new HairService(service.getName(), service.getDuration(), service.getPrice(), hairType);
        }
        boolean success = appointmentService.addService(newService);
        return success ? "redirect:/appointments/book" : "error";
    }
}