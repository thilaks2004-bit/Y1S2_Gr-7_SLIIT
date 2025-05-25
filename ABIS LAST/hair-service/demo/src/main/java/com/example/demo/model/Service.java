package com.example.demo.model;

public abstract class Service {
    private String name;
    private int duration;
    private double price;
    private String category;
    private String description;

    public Service() {
    }

    public Service(String name, int duration, double price, String category, String description) {
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.category = category;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract String displayDetails();

    @Override
    public String toString() {
        return name + "," + duration + "," + price + "," + category + "," + description;
    }

    public static Service fromString(String line) {
        String[] parts = line.split(",", 6);
        if (parts.length == 6 && "Hair".equals(parts[3].trim())) {
            return new HairService(
                    parts[0].trim(),
                    Integer.parseInt(parts[1].trim()),
                    Double.parseDouble(parts[2].trim()),
                    parts[5].trim()
            );
        } else if (parts.length >= 5) {
            return new GenericService(
                    parts[0].trim(),
                    Integer.parseInt(parts[1].trim()),
                    Double.parseDouble(parts[2].trim()),
                    parts[3].trim(),
                    parts[4].trim()
            );
        } else {
            throw new IllegalArgumentException("Invalid service format: " + line);
        }
    }
}