package com.stylesync.salonreview.service;

import java.io.IOException;
import java.util.List;

public interface FeedbackService<T> {
    void create(T feedback) throws IOException;
    List<T> readAll() throws IOException;
    void update(T feedback) throws IOException;
    void delete(String customerName) throws IOException;
}