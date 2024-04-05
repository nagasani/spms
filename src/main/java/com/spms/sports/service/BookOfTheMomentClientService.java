package com.spms.sports.service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spms.sports.entity.Book;

@Service
public class BookOfTheMomentClientService 
{
    private final RestTemplate restTemplate;
    private final String bookOfTheMomentUrl = "http://localhost:8081/api/books/book-of-the-moment";
    private Book currentBook;

    public BookOfTheMomentClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 2000) // 2 hours = 7,200,000 milliseconds
    public void fetchAndUpdateBookOfTheMoment() 
    {
    	String prevBook = this.currentBook == null ? "":this.currentBook.getTitle();
        Book fetchedBook = restTemplate.getForObject(bookOfTheMomentUrl, Book.class);
        if (fetchedBook != null && !fetchedBook.equals(currentBook)) 
        {
            currentBook = fetchedBook;
            if(!prevBook.equalsIgnoreCase(currentBook.getTitle())) 
            {
            	System.out.println("Updated Book of the Moment: " + fetchedBook.getTitle()+" ID :"+fetchedBook.getId());
            }            
        }
    }
    
    

    public Book getCurrentBook() {
        return currentBook;
    }
}
