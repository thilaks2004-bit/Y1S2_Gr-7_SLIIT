package com.example.demo.repository;

import com.example.demo.model.Service;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServiceRepository {
    private static final String FILE_PATH = "services.txt";

    public boolean saveService(Service service) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(service.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Service> findAll() {
        List<Service> services = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return services;
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    try {
                        services.add(Service.fromString(line));
                    } catch (Exception e) {
                        System.err.println("Error parsing line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return services;
    }
}