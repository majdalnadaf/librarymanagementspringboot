package com.librarymanagement.librarymanagement.api;


import com.librarymanagement.librarymanagement.application.services.BorrowingService;
import com.librarymanagement.librarymanagement.domain.BorrowingRecord;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RequestMapping("/api/borrow")
public class BorrowingController {

    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping("/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse<BorrowingRecord>> borrowBook(
            @PathVariable Long bookId, @PathVariable Long patronId) {

        Either<String, BorrowingRecord> result = borrowingService.borrowBook(bookId, patronId);

        if (result.isRight()) {
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), null, result.get()));
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", result.getLeft());
            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.toString(), errors, null));
        }
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse<BorrowingRecord>> returnBook(
            @PathVariable Long bookId, @PathVariable Long patronId) {

        Either<String, BorrowingRecord> result = borrowingService.returnBook(bookId, patronId);

        if (result.isRight()) {
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), null, result.get()));
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", result.getLeft());
            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.toString(), errors, null));
        }
    }
}