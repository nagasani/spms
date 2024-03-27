package com.spms.sports.entity;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)	
	private Set<Book> books = new HashSet<>();

	@Column(length = 4000) // Adjust the length according to your needs
    private String biography;
	
	public Author() {
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + ", books=" + books + ", biography=" + biography + "]";
	}

	public Author(String name) {
		this.name = name;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	// Helper method to add a book to the author
	public void addBook(Book book) {
		books.add(book);
		book.setAuthor(this);
	}

	// Helper method to remove a book from the author
	public void removeBook(Book book) {
		books.remove(book);
		book.setAuthor(null);
	}
	
	public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
