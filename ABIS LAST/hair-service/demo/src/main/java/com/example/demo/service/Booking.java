package com.example.demo.service;

import com.example.demo.model.Appointment;

public abstract class Booking {
    public abstract boolean bookAppointment(Appointment appointment);
    public abstract boolean checkAvailability(Appointment appointment);
}