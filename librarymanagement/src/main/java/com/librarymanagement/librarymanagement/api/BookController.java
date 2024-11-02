package com.librarymanagement.librarymanagement.api;


import com.librarymanagement.librarymanagement.application.services.BookService;
import com.librarymanagement.librarymanagement.domain.Book;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;


@RequestMapping("/api/books")
public class BookController extends BaseController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks() {
        Either<String, List<Book>> books = bookService.getAllBooks();
        if (books.isRight()) {
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), null, books.get()));
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", books.getLeft());
            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.toString(), errors, null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Long id) {
        Either<String, Book> book = bookService.getBookById(id);
        if (book.isRight()) {
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), null, book.get()));
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", book.getLeft());
            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.toString(), errors, null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Book>> addBook(@Valid @RequestBody Book book) {
        Either<String, Book> createdBook = bookService.addBook(book);
        if (createdBook.isRight()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.toString(), null, createdBook.get()));
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", createdBook.getLeft());
            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.toString(), errors, null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        Either<String, Book> updatedBook = bookService.updateBook(id, book);
        if (updatedBook.isRight()) {
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), null, updatedBook.get()));
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", updatedBook.getLeft());
            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.toString(), errors, null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        Either<String, String> result = bookService.deleteBook(id);
        if (result.isRight()) {
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), null, null));
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", result.getLeft());
            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.toString(), errors, null));
        }
    }
}
