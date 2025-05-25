package com.stylesync.salonreview.service;

import com.stylesync.salonreview.model.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService implements FeedbackService<Review> {
    @Value("${reviews.file.path}")
    private String filePath;

    private static final String DELIMITER = "|";

    public ReviewService() {
        System.out.println("ReviewService initialized with filePath: " + filePath);
    }

    @Override
    public void create(Review review) throws IOException {
        List<Review> reviews = readAll();
        reviews.add(review);
        writeAll(reviews);
    }

    @Override
    public List<Review> readAll() throws IOException {
        List<Review> reviews = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
            return reviews;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\" + DELIMITER, -1);
                if (parts.length == 6) {
                    Review review = new Review();
                    review.setCustomerName(parts[0]);
                    review.setService(parts[1]);
                    review.setRating(Integer.parseInt(parts[2]));
                    review.setDate(parts[3]);
                    review.setComments(parts[4]);
                    review.setResponse(parts[5]);
                    reviews.add(review);
                }
            }
        }
        return reviews;
    }

    @Override
    public void update(Review review) throws IOException {
        List<Review> reviews = readAll();
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getCustomerName().equals(review.getCustomerName())) {
                reviews.set(i, review);
                break;
            }
        }
        writeAll(reviews);
    }

    @Override
    public void delete(String customerName) throws IOException {
        List<Review> reviews = readAll();
        reviews.removeIf(r -> r.getCustomerName().equals(customerName));
        writeAll(reviews);
    }

    private void writeAll(List<Review> reviews) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Review review : reviews) {
                String line = String.join(DELIMITER,
                        review.getCustomerName(),
                        review.getService(),
                        String.valueOf(review.getRating()),
                        review.getDate(),
                        review.getComments(),
                        review.getResponse() != null ? review.getResponse() : "");
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            throw e;
        }
    }
}