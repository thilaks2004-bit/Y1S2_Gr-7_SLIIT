package com.beautysalon.productmanagement.service;

import com.beautysalon.productmanagement.model.HairProduct;
import com.beautysalon.productmanagement.model.Product;
import com.beautysalon.productmanagement.model.SkinProduct;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

@Service
public class ProductService {

    private final String productFile = "data/products.json";  // Path to your JSON file
    public List<Product> getAllProducts() {
        try (Reader reader = new FileReader("data/products.json")) {
            Gson gson = new Gson();
            Type productListType = new TypeToken<List<Product>>() {}.getType();
            List<Product> products = gson.fromJson(reader, productListType);
            return products != null ? products : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void addProduct(Product product) {
        String id = UUID.randomUUID().toString();
        Product newProduct;

        if ("Hair".equalsIgnoreCase(product.getCategory())) {
            newProduct = new HairProduct(id, product.getName(), product.getPrice(), product.getStock(), product.getImage(), product.getDescription());
        } else if ("Skin".equalsIgnoreCase(product.getCategory())) {
            newProduct = new SkinProduct(id, product.getName(), product.getPrice(), product.getStock(), product.getImage(), product.getDescription());
        } else {
            newProduct = new Product(id, product.getName(), product.getCategory(), product.getPrice(), product.getStock(), product.getImage(), product.getDescription());
        }

        List<Product> products = getAllProducts();
        products.add(newProduct);
        saveAllProducts(products);
    }

    public void saveAllProducts(List<Product> products) {
        try (Writer writer = new FileWriter("data/products.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(products, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean updateProduct(String id, Product updatedProduct) {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if (product.getId().equals(id)) {
                product.setName(updatedProduct.getName());
                product.setCategory(updatedProduct.getCategory());
                product.setPrice(updatedProduct.getPrice());
                product.setStock(updatedProduct.getStock());
                product.setImage(updatedProduct.getImage());
                product.setDescription(updatedProduct.getDescription());
                saveProducts(products);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id) {
        List<Product> products = loadProducts();
        boolean removed = products.removeIf(p -> p.getId().equals(id));
        if (removed) {
            saveProducts(products);
        }
        return removed;
    }

    private List<Product> loadProducts() {
        return null;
    }


    public Product getProductById(String id) {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    private void saveProducts(List<Product> products) {
        try (FileWriter writer = new FileWriter(productFile)) {
            new Gson().toJson(products, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




