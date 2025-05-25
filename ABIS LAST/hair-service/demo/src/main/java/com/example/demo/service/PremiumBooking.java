package com.example.demo.service;

import com.example.demo.model.Appointment;

public class PremiumBooking extends Booking {
    private final AppointmentService appointmentService;

    public PremiumBooking(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public boolean bookAppointment(Appointment appointment) {
        appointmentService.deleteAppointmentByTimeSlot(appointment.getTimeSlot());
        return appointmentService.createAppointment(appointment);
    }

    @Override
    public boolean checkAvailability(Appointment appointment) {
        return true;
    }
}