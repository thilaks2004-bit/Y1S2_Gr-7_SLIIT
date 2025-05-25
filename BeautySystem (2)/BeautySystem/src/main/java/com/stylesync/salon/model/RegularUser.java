package com.stylesync.salon.model;

public class RegularUser extends User {
    public RegularUser() {}

    public RegularUser(Long id, String username, String password, String email, String membershipType) {
        super(id, username, password, email, membershipType);
    }

    @Override
    public String getUserType() {
        return "Regular";
    }
}