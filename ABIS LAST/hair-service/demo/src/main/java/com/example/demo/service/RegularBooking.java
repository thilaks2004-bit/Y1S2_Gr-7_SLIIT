package com.example.demo.service;

import com.example.demo.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public class RegularBooking extends Booking {
    private final AppointmentService appointmentService;

    public RegularBooking(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public boolean bookAppointment(Appointment appointment) {
        if (checkAvailability(appointment)) {
            return appointmentService.createAppointment(appointment);
        }
        return false;
    }

    @Override
    public boolean checkAvailability(Appointment appointment) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        LocalDateTime timeSlot = appointment.getTimeSlot();
        return appointments.stream()
                .noneMatch(a -> a.getTimeSlot().equals(timeSlot));
    }
}