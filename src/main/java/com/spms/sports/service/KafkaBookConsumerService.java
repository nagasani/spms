package com.spms.sports.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.spms.sports.entity.Book;

@Service
public class KafkaBookConsumerService 
{
    private static final Logger logger = LoggerFactory.getLogger(KafkaBookConsumerService.class);
    
    private final ObjectMapper objectMapper;

    public KafkaBookConsumerService() 
    {
        this.objectMapper = new ObjectMapper();
        // Enable pretty printing
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    @KafkaListener(topics = "new-books", groupId = "book-consumers")
    public void listenForBookMessages(String message) 
    {
        try 
        {
            Book book = objectMapper.readValue(message, Book.class);
            // Convert the book object back to a pretty JSON string for logging
            String prettyBookJson = objectMapper.writeValueAsString(book);
            logger.info("Received message: {}", prettyBookJson);            
        } 
        catch (Exception e) 
        {
            logger.error("Failed to deserialize the book message", e);
        }
    }
}
