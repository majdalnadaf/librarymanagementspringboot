package com.librarymanagement.librarymanagement.domain;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class BorrowingRecord {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Book is required")
    @ManyToOne(optional = false)
    private Book book;

    @NotNull(message = "Patron is required")
    @ManyToOne(optional = false)
    private Patron patron;

    @NotNull(message = "Borrowing date is required")
    private LocalDate borrowingDate;

    private LocalDate returnDate;
    private Boolean returned;

    // Constructors...

    public BorrowingRecord(Book book, Patron patron, LocalDate borrowingDate ,LocalDate returnDate , Boolean returned ) {
        this.book = book;
        this.patron = patron;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
        this.returned = returned;
    }

    public BorrowingRecord() {
    }

    // Getters and setters...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowingRecord that = (BorrowingRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Book getBook() {
        return book;
    }

    public Patron getPatron() {
        return patron;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    public Boolean getReturned() {
        return returned;
    }

    public LocalDate getBorrowingDate() {
        return borrowingDate;
    }
}
