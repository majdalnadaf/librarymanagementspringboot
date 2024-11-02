package com.librarymanagement.librarymanagement.application.common.interfaces;

import com.librarymanagement.librarymanagement.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {

     Boolean existsByIsbn(String isbn);
}

