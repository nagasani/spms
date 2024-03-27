package com.spms.sports.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spms.sports.entity.Book;

@Service
public class KafkaBookConsumerService 
{
    private static final Logger logger = LoggerFactory.getLogger(KafkaBookConsumerService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "new-books", groupId = "book-consumers")
    public void listenForBookMessages(String message) {
        try 
        {
            Book book = objectMapper.readValue(message, Book.class);
            logger.info("Received message: {}", book);            
        } 
        catch (Exception e) 
        {
            logger.error("Failed to deserialize the book message", e);
        }
    }
}
