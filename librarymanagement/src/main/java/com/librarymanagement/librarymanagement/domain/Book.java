package com.librarymanagement.librarymanagement.domain;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Year;
import java.util.Objects;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    @NotBlank(message = "Title is required")
    @Size(max = 300, message = "Title cannot exceed 300 characters")
    @Column(nullable = false, length = 300)
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 200, message = "Author name cannot exceed 200 characters")
    @Column(name = "author", length = 200, nullable = false)
    private String author;

    @NotNull(message = "Publication year is required")
    @Column(nullable = false)
    private Year publicationYear;

    @NotBlank(message = "ISBN is required")
    @Column(name = "ISBN", nullable = false, unique = true)
    private String isbn;

    @Column(name = "IsAvailable")
    private Boolean isAvailable = true;

    // Constructors...

    public Book() {

    }

    public Book(String title, String author, Year publicationYear, String isbn) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
    }

    // Getters and setters...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublicationYear(Year publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }


    public String getAuthor() {
        return author;
    }

    public Year getPublicationYear() {
        return publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean isAvailable)
    {
        this.isAvailable = isAvailable;
    }

}
