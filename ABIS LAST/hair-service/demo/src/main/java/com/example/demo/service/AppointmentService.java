package com.example.demo.service;

import com.example.demo.model.Appointment;
import com.example.demo.model.Service;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;

@org.springframework.stereotype.Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public boolean createAppointment(Appointment appointment) {
        return appointmentRepository.saveAppointment(appointment);
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        quickSort(appointments, 0, appointments.size() - 1);
        return appointments;
    }

    public boolean updateAppointment(String id, Appointment updatedAppointment) {
        return appointmentRepository.updateAppointment(id, updatedAppointment);
    }

    public boolean deleteAppointment(String id) {
        return appointmentRepository.deleteAppointment(id);
    }

    public boolean deleteAppointmentByTimeSlot(LocalDateTime timeSlot) {
        return appointmentRepository.deleteByTimeSlot(timeSlot);
    }

    public Queue<Appointment> getBookingQueue() {
        return appointmentRepository.getBookingQueue();
    }

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public boolean addService(Service service) {
        return serviceRepository.saveService(service);
    }

    private void quickSort(List<Appointment> appointments, int low, int high) {
        if (low < high) {
            int pi = partition(appointments, low, high);
            quickSort(appointments, low, pi - 1);
            quickSort(appointments, pi + 1, high);
        }
    }

    private int partition(List<Appointment> appointments, int low, int high) {
        LocalDateTime pivot = appointments.get(high).getTimeSlot();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (appointments.get(j).getTimeSlot().isBefore(pivot)) {
                i++;
                Appointment temp = appointments.get(i);
                appointments.set(i, appointments.get(j));
                appointments.set(j, temp);
            }
        }
        Appointment temp = appointments.get(i + 1);
        appointments.set(i + 1, appointments.get(high));
        appointments.set(high, temp);
        return i + 1;
    }
}