package com.stylesync.salonreview.controller;

import com.stylesync.salonreview.model.Review;
import com.stylesync.salonreview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestBody Review review) {
        try {
            review.submit();
            reviewService.create(review);
            return ResponseEntity.ok("Review submitted successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error submitting review: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Review> getAllReviews() throws IOException {
        return reviewService.readAll();
    }

    @PutMapping
    public ResponseEntity<String> updateReview(@RequestBody Review review) {
        try {
            reviewService.update(review);
            return ResponseEntity.ok("Review updated successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error updating review: " + e.getMessage());
        }
    }

    @DeleteMapping("/{customerName}")
    public ResponseEntity<String> deleteReview(@PathVariable String customerName) {
        try {
            reviewService.delete(customerName);
            return ResponseEntity.ok("Review deleted successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error deleting review: " + e.getMessage());
        }
    }
}