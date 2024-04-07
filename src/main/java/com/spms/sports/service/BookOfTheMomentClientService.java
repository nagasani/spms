package com.spms.sports.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spms.sports.entity.Book;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class BookOfTheMomentClientService 
{
    private final RestTemplate restTemplate;
    @Value("${bookofthemoment.url}")
    private String bookOfTheMomentUrl;
    private Book currentBook;

    public BookOfTheMomentClientService(RestTemplate restTemplate) 
    {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRateString = "${scheduled.rate}") // Use SpEL to read from application.properties
    public void scheduledBookUpdate() 
    {
    	try 
    	{
    		fetchAndUpdateBookOfTheMoment();
    	}
    	catch (Exception e) 
    	{
			System.out.println(e.getMessage());
		} 
    }

    @CircuitBreaker(name = "bookServiceCircuitBreaker", fallbackMethod = "fallbackBook")
    public void fetchAndUpdateBookOfTheMoment() 
    {
        try 
        {
            Long prevBookID = this.currentBook == null ? Long.MAX_VALUE : this.currentBook.getId();
            System.out.println("Attempting to fetch the book of the moment");
            Book fetchedBook = restTemplate.getForObject(bookOfTheMomentUrl, Book.class);
            if (fetchedBook != null && !fetchedBook.equals(currentBook)) 
            {
                currentBook = fetchedBook;
                if (prevBookID.longValue() != fetchedBook.getId().longValue()) 
                {
                    System.out.println("Updated Book of the Moment: " + fetchedBook.getTitle() + " ID :" + fetchedBook.getId());
                }
            }
        } 
        catch (Exception ex) 
        {
            System.out.println("Error during fetching book of the moment: " + ex.getMessage());
            throw ex; // Rethrow the exception to ensure it reaches the circuit breaker
        }
    }

    public Book fallbackBook(Exception ex) 
    {
        // Fallback logic, like returning a default Book instance or a cached value
        System.out.println("Fallback method called due to: " + ex.getMessage());
        return this.currentBook; // return the current book if the service fails
    }

    public Book getCurrentBook() 
    {
        return currentBook;
    }
}
