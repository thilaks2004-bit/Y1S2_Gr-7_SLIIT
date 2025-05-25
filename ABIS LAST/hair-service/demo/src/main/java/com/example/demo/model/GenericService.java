package com.example.demo.model;

public class GenericService extends Service {
    public GenericService() {
        super("", 0, 0.0, "", "");
    }

    public GenericService(String name, int duration, double price, String category, String description) {
        super(name, duration, price, category, description);
    }

    @Override
    public String displayDetails() {
        return "Service: " + getName() + " | Duration: " + getDuration() + " mins | Price: Rs" + getPrice() + " | Category: " + getCategory();
    }
}