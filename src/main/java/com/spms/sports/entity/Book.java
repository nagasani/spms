package com.spms.sports.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Book {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message= "Title Cannot be blank")
    private String title;

    @Min(value=0, message="Min price shoud be 0")
    @Max(value=10000, message="Min price shoud be 10000")
    private double price;
    
    private String status; // Added status field

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToMany
    @JoinTable(
      name = "book_category",
      joinColumns = @JoinColumn(name = "book_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    // Constructors
    public Book() {
    }

    public Book(Long id, String title, double price, Author author, String status) {
    	this.id = id;
        this.title = title;
        this.price = price;
        this.author = author;
        this.status = status; // Initialize status
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
    
    public String getStatus() { // Getter for status
        return status;
    }

    public void setStatus(String status) { // Setter for status
        this.status = status;
    }

    // Additional helper method to associate a book with a category
    public void addCategory(Category category) {
        this.categories.add(category);
        category.getBooks().add(this);
    }

    // Additional helper method to associate a book with an author
    public void setAuthorAndLinkToBook(Author author) {
        setAuthor(author);
        author.getBooks().add(this);
    }
    
    @Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", price=" + price + ", status=" + status + ", author=" + author
				+ ", categories=" + categories + "]";
	}
}