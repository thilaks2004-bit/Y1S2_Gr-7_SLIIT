package com.stylesync.salon.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public abstract class User {
    private Long id;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Email(message = "Invalid email format")
    @Pattern(regexp = ".*@gmail\\.com$", message = "Email must be a Gmail address")
    private String email;

    private String membershipType;

    public User() {}

    public User(Long id, String username, String password, String email, String membershipType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.membershipType = membershipType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }

    public abstract String getUserType();

    public void setUserType(String regular) {
    }
}