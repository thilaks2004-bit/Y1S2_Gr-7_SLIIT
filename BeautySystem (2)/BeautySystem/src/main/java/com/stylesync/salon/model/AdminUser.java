package com.stylesync.salon.model;

public class AdminUser extends User {
    public AdminUser() {}

    public AdminUser(Long id, String username, String password, String email, String membershipType) {
        super(id, username, password, email, membershipType);
    }

    @Override
    public String getUserType() {
        return "Admin";
    }
}