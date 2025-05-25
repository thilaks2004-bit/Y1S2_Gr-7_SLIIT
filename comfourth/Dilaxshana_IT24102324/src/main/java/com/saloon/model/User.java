package com.saloon.model;

public class User {
    private static int count = 1;
    private String id;
    private String username;
    private String email;
    private String password;
    private String contactNo;
    private String address;
    private String role;

    public User() {
        this.id = String.format("#%03d", count);
        count++;
    }

    public User(String username, String email, String password, String contactNo, String address, String role) {
        this.id = String.format("#%03d", count);
        count++;
        this.username = username;
        this.email = email;
        this.password = password;
        this.contactNo = contactNo;
        this.address = address;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return id + "," + username + "," + email + "," + password + "," + contactNo + "," + address + "," + role;
    }

    public static User fromString(String str) {
        String[] parts = str.split(",");
        User user = new User(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
        user.setId(parts[0]);
        return user;
    }
}

