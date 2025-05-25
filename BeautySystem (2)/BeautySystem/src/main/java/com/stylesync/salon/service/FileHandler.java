package com.stylesync.salon.service;

import com.stylesync.salon.model.User;
import com.stylesync.salon.model.RegularUser;
import com.stylesync.salon.model.AdminUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileHandler {
    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);
    private static final String BASE_PATH = System.getProperty("user.dir") + "/data";
    private static final String FILE_PATH = BASE_PATH + "/users.txt";

    public FileHandler() {
        try {
            Files.createDirectories(Paths.get(BASE_PATH));
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
                logger.info("Created users.txt at: {}", file.getAbsolutePath());
            } else {
                logger.info("users.txt already exists at: {}", file.getAbsolutePath());
            }
        } catch (IOException e) {
            logger.error("Failed to initialize file storage: {}", e.getMessage(), e);
        }
    }

    public void saveUser(User user) throws IOException {
        if (user == null) {
            logger.error("Cannot save user: User object is null");
            throw new IllegalArgumentException("User cannot be null");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String userData = String.format("%d,%s,%s,%s,%s,%s",
                    user.getId(), user.getUsername(), user.getPassword(),
                    user.getEmail(), user.getMembershipType(), user.getUserType());
            writer.write(userData);
            writer.newLine();
            writer.flush();
            logger.info("Successfully saved user: {} to users.txt", user.getUsername());
        } catch (IOException e) {
            logger.error("Failed to save user to users.txt: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<User> readUsers() throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            logger.warn("users.txt does not exist, creating new file at: {}", file.getAbsolutePath());
            file.createNewFile();
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length == 6) {
                    User user = data[5].equals("Admin") ? new AdminUser() : new RegularUser();
                    user.setId(Long.parseLong(data[0]));
                    user.setUsername(data[1]);
                    user.setPassword(data[2]);
                    user.setEmail(data[3]);
                    user.setMembershipType(data[4]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to read users: {}", e.getMessage(), e);
            throw e;
        }
        return users;
    }

    public void updateUser(User updatedUser) throws IOException {
        List<User> users = readUsers();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                if (user.getId().equals(updatedUser.getId())) {
                    writer.write(String.format("%d,%s,%s,%s,%s,%s",
                            updatedUser.getId(), updatedUser.getUsername(), updatedUser.getPassword(),
                            updatedUser.getEmail(), updatedUser.getMembershipType(), updatedUser.getUserType()));
                } else {
                    writer.write(String.format("%d,%s,%s,%s,%s,%s",
                            user.getId(), user.getUsername(), user.getPassword(),
                            user.getEmail(), user.getMembershipType(), user.getUserType()));
                }
                writer.newLine();
            }
            logger.info("Updated user: {}", updatedUser.getUsername());
        } catch (IOException e) {
            logger.error("Failed to update user: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void deleteUser(Long id) throws IOException {
        List<User> users = readUsers();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                if (!user.getId().equals(id)) {
                    writer.write(String.format("%d,%s,%s,%s,%s,%s",
                            user.getId(), user.getUsername(), user.getPassword(),
                            user.getEmail(), user.getMembershipType(), user.getUserType()));
                    writer.newLine();
                }
            }
            logger.info("Deleted user with ID: {}", id);
        } catch (IOException e) {
            logger.error("Failed to delete user: {}", e.getMessage(), e);
            throw e;
        }
    }
}