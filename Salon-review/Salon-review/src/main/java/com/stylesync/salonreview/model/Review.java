package com.stylesync.salonreview.model;

import java.time.LocalDate;

public class Review extends Feedback {
    private String service;
    private int rating;
    private String comments;
    private String response;

    @Override
    public void submit() {
        this.date = LocalDate.now().toString();
    }

    // Getters and Setters (Encapsulation)
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
}