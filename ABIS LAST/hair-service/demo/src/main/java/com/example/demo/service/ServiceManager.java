package com.example.demo.service;

import com.example.demo.model.HairService;
import com.example.demo.model.Service;
import com.example.demo.model.SkinService;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceManager {
    private static final String FILE_PATH = "C:\\Users\\anant\\OneDrive\\Desktop\\hair-service\\demo\\src\\main\\resources\\static\\services.txt";

    public void addService(Service service) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(service.toString());
            writer.newLine();
        }
    }

    public List<Service> getAllServices() throws IOException {
        List<Service> services = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue;
                String name = parts[0];
                int duration = Integer.parseInt(parts[1]);
                double price = Double.parseDouble(parts[2]);
                String category = parts[3];
                if (category.equals("Hair")) {
                    String hairType = parts[4];
                    services.add(new HairService(name, duration, price, hairType));
                } else if (category.equals("Skin")) {
                    String skinType = parts[4];
                    services.add(new SkinService(name, duration, price, skinType));
                }
            }
        }
        return services;
    }

    public List<Service> searchServices(String keyword) throws IOException {
        List<Service> allServices = getAllServices();
        List<Service> result = new ArrayList<>();
        for (Service service : allServices) {
            if (service.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    service.getCategory().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(service);
            }
        }
        return result;
    }

    public void updateService(String oldName, Service updatedService) throws IOException {
        List<Service> services = getAllServices();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Service service : services) {
                if (service.getName().equals(oldName)) {
                    writer.write(updatedService.toString());
                } else {
                    writer.write(service.toString());
                }
                writer.newLine();
            }
        }
    }

    public void deleteService(String name) throws IOException {
        List<Service> services = getAllServices();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Service service : services) {
                if (!service.getName().equals(name)) {
                    writer.write(service.toString());
                    writer.newLine();
                }
            }
        }
    }
}