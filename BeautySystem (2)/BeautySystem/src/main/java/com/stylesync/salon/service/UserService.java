package com.stylesync.salon.service;

import com.stylesync.salon.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private FileHandler fileHandler;

    public void registerUser(User user) throws IOException {
        if (user == null) {
            logger.error("Cannot register user: User object is null");
            throw new IllegalArgumentException("User cannot be null");
        }
        List<User> users = fileHandler.readUsers();
        user.setId(users.stream().mapToLong(User::getId).max().orElse(0L) + 1);
        user.setUserType("Regular");
        fileHandler.saveUser(user);
        logger.info("User registered successfully: {}", user.getUsername());
    }


    public User findUserByUsername(String username) throws IOException {
        return fileHandler.readUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public User findUserById(Long id) throws IOException {
        return fileHandler.readUsers().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers() throws IOException {
        return fileHandler.readUsers();
    }

    public void updateUser(User user) throws IOException {
        fileHandler.updateUser(user);
    }

    public void deleteUser(Long id) throws IOException {
        fileHandler.deleteUser(id);
    }

    public boolean validateAdminLogin(String username, String password) throws IOException {
        if (username == null || password == null) {
            logger.warn("Invalid admin login attempt: username or password is null");
            return false;
        }
        User admin = fileHandler.readUsers().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password) && "Admin".equals(u.getUserType()))
                .findFirst()
                .orElse(null);
        if (admin != null) {
            logger.info("Admin validated: {}", username);
            return true;
        }
        return false;
    }
}