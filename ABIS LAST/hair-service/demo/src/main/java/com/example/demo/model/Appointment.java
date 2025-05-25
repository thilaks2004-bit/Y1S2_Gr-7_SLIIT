package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment {
    private String id;
    private String customerName;
    private LocalDateTime timeSlot;
    private String serviceType;
    private String userType;

    public Appointment() {
        this.id = UUID.randomUUID().toString();
    }

    public Appointment(String customerName, LocalDateTime timeSlot, String serviceType, String userType) {
        this.id = UUID.randomUUID().toString();
        this.customerName = customerName;
        this.timeSlot = timeSlot;
        this.serviceType = serviceType;
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(LocalDateTime timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return id + "," + customerName + "," + timeSlot.toString() + "," + serviceType + "," + userType;
    }

    public static Appointment fromString(String line) {
        String[] parts = line.split(",", 5);
        Appointment appointment = new Appointment();
        appointment.setId(parts[0]);
        appointment.setCustomerName(parts[1]);
        appointment.setTimeSlot(LocalDateTime.parse(parts[2]));
        appointment.setServiceType(parts[3]);
        appointment.setUserType(parts[4]);
        return appointment;
    }
}