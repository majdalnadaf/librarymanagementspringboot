package com.librarymanagement.librarymanagement.api;



import com.librarymanagement.librarymanagement.application.services.PatronService;
import com.librarymanagement.librarymanagement.domain.Patron;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/api/patrons")
public class PatronController {

    private final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Patron>>> getAllPatrons() {
        Either<String, List<Patron>> patrons = patronService.getAllPatrons();
        if (patrons.isRight()) {
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), null, patrons.get()));
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", patrons.getLeft());
            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.toString(), errors, null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Patron>> getPatronById(@PathVariable Long id) {
        Either<String, Patron> patron = patronService.getPatronById(id);
        if (patron.isRight()) {
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), null, patron.get()));
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", patron.getLeft());
            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.toString(), errors, null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Patron>> addPatron(@Valid @RequestBody Patron patron) {
        Either<String, Patron> createdPatron = patronService.addPatron(patron);
        if (createdPatron.isRight()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.toString(), null, createdPatron.get()));
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", createdPatron.getLeft());
            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.toString(), errors, null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Patron>> updatePatron(@PathVariable Long id, @Valid @RequestBody Patron patron) {
        Either<String, Patron> updatedPatron = patronService.updatePatron(id, patron);
        if (updatedPatron.isRight()) {
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), null, updatedPatron.get()));
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", updatedPatron.getLeft());
            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.toString(), errors, null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePatron(@PathVariable Long id) {
        Either<String, String> result = patronService.deletePatron(id);
        if (result.isRight()) {
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), null, null));
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", result.getLeft());
            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.toString(), errors, null));
        }
    }
}