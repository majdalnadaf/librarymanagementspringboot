package com.librarymanagement.librarymanagement.application.services;



import com.librarymanagement.librarymanagement.application.common.interfaces.IBookRepository;
import com.librarymanagement.librarymanagement.application.common.interfaces.IBorrowingRecordRepository;
import com.librarymanagement.librarymanagement.application.common.interfaces.IPatronRepository;
import com.librarymanagement.librarymanagement.domain.Book;
import com.librarymanagement.librarymanagement.domain.BorrowingRecord;
import com.librarymanagement.librarymanagement.domain.Patron;
import io.vavr.control.Either;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingService {

    private final IBorrowingRecordRepository borrowingRecordRepository;
    private final IBookRepository bookRepository;
    private final IPatronRepository patronRepository;

    public BorrowingService(IBorrowingRecordRepository borrowingRecordRepository, IBookRepository bookRepository, IPatronRepository patronRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    @Transactional
    public Either<String, BorrowingRecord> borrowBook(Long bookId, Long patronId) {

        Optional<Book> bookOpt = bookRepository.findById(bookId);
        Optional<Patron> patronOpt = patronRepository.findById(patronId);

        if (bookOpt.isEmpty()) {
            return Either.left("Book not found with ID: " + bookId);
        }
        if (patronOpt.isEmpty()) {
            return Either.left("Patron not found with ID: " + patronId);
        }

        Book book = bookOpt.get();

        if(!book.getAvailable())
        {
            return Either.left("Book not available");
        }

        Patron patron = patronOpt.get();
        LocalDate borrowingDate  = LocalDate.now();
        LocalDate returnDate = borrowingDate.plusDays(15); // you should access this value "15" from db...

        // Update book in db...
        book.setAvailable(false);
        BorrowingRecord borrowingRecord = new BorrowingRecord(book, patron,borrowingDate,returnDate,false);

        bookRepository.save(book);
        BorrowingRecord savedRecord = borrowingRecordRepository.save(borrowingRecord);
        return Either.right(savedRecord);
    }

    @Transactional
    public Either<String, BorrowingRecord> returnBook(Long bookId, Long patronId) {

        Optional<BorrowingRecord> recordOpt =
                borrowingRecordRepository.findByBookIdAndPatronIdAndReturnedFalse(bookId, patronId);

        if (recordOpt.isEmpty()) {
            return Either.left("Borrowing record not found for the specified book and patron");
        }


        BorrowingRecord record = recordOpt.get();
        record.setReturned(true);  // Mark as returned

        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if(bookOpt.isEmpty())
        {
            return Either.left("Book not found with id" + bookId);
        }

        Book book = bookOpt.get();
        book.setAvailable(true);

        bookRepository.save(book);

        BorrowingRecord updatedRecord = borrowingRecordRepository.save(record);
        return Either.right(updatedRecord);
    }
}
