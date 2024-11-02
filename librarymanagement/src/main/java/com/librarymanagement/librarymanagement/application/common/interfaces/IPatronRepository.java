package com.librarymanagement.librarymanagement.application.common.interfaces;


import com.librarymanagement.librarymanagement.domain.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface IPatronRepository extends JpaRepository<Patron, Long> {

    Boolean existsByContactInfo(String contactInfo);
}
