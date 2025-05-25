package com.beautysalon.productmanagement.model;

public class Product {
    private String id;
    private String name;
    private String category;
    private double price;
    private int stock;
    private String imageUrl;
    private String description;

    public Product() {}

    public Product(String id, String name, String category, double price, int stock, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.description = description;
    }
    public class HairProduct extends Product {
        public HairProduct(String id, String name, double price, int stock, String imageUrl, String description) {
            super(id, name, "Hair", price, stock, imageUrl, description);
        }
    }

    public class SkinProduct extends Product {
        public SkinProduct(String id, String name, double price, int stock, String imageUrl, String description) {
            super(id, name, "Skin", price, stock, imageUrl, description);
        }
    }


    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getImage() { return imageUrl; }
    public void setImage(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
