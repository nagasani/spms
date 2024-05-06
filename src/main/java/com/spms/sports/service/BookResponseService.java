package com.spms.sports.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookResponseService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    //@KafkaListener(topics = "new-books", groupId = "book-consumers")

    @KafkaListener(topics = "book-request", groupId = "bookResponseGroup")
    public void handleBookRequest(String bookId) 
    {
        String responseTopic = "book-response";

        // Simulate processing and fetching book details
        String bookDetails = "Details of Book ID: " + bookId; // Example response
        
        System.out.println("bookDetails : "+bookDetails);

        // Send the response back to the response topic
        kafkaTemplate.send(responseTopic, bookDetails);
    }
}