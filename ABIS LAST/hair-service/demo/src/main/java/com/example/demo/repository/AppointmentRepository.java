package com.example.demo.repository;

import com.example.demo.model.Appointment;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Repository
public class AppointmentRepository {
    private static final String FILE_PATH = "appointments.txt";
    private final Queue<Appointment> bookingQueue = new LinkedList<>();

    public boolean saveAppointment(Appointment appointment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(appointment.toString());
            writer.newLine();
            bookingQueue.offer(appointment);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    try {
                        appointments.add(Appointment.fromString(line));
                    } catch (Exception e) {
                        System.err.println("Error parsing line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public boolean updateAppointment(String id, Appointment updatedAppointment) {
        List<Appointment> appointments = findAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            boolean updated = false;
            for (Appointment appointment : appointments) {
                if (appointment.getId().equals(id)) {
                    writer.write(updatedAppointment.toString());
                    updated = true;
                } else {
                    writer.write(appointment.toString());
                }
                writer.newLine();
            }
            return updated;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAppointment(String id) {
        List<Appointment> appointments = findAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            boolean deleted = false;
            for (Appointment appointment : appointments) {
                if (!appointment.getId().equals(id)) {
                    writer.write(appointment.toString());
                    writer.newLine();
                } else {
                    deleted = true;
                }
            }
            return deleted;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteByTimeSlot(LocalDateTime timeSlot) {
        List<Appointment> appointments = findAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            boolean deleted = false;
            for (Appointment appointment : appointments) {
                if (!appointment.getTimeSlot().equals(timeSlot)) {
                    writer.write(appointment.toString());
                    writer.newLine();
                } else {
                    deleted = true;
                }
            }
            return deleted;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Queue<Appointment> getBookingQueue() {
        return bookingQueue;
    }
}
