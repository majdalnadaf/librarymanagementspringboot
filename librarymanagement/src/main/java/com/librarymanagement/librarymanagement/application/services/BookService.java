package com.librarymanagement.librarymanagement.application.services;




import com.librarymanagement.librarymanagement.application.common.interfaces.IBookRepository;
import com.librarymanagement.librarymanagement.domain.Book;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final IBookRepository bookRepository;

    public BookService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Either<String, List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.isEmpty() ? Either.left("No books found") : Either.right(books);
    }

    public Either<String, Book> getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(Either::<String, Book>right).orElseGet(() -> Either.left("Book not found"));
    }

    public Either<String, Book> addBook(Book book) {

        if (bookRepository.existsByIsbn(book.getIsbn())) {
            return Either.left("A book with this ISBN already exists");
        }
        Book savedBook = bookRepository.save(book);
        return Either.right(savedBook);
    }

    public Either<String, Book> updateBook(Long id, Book bookDetails) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(bookDetails.getTitle());
                    book.setAuthor(bookDetails.getAuthor());
                    book.setPublicationYear(bookDetails.getPublicationYear());
                    book.setIsbn(bookDetails.getIsbn());
                    bookRepository.save(book);
                    return Either.<String, Book>right(book);
                })
                .orElseGet(() -> Either.left("Book not found"));
    }

    public Either<String, String> deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            return Either.left("Book not found");
        }
        bookRepository.deleteById(id);
        return Either.right("Book deleted successfully");
    }
}

