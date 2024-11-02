package com.librarymanagement.librarymanagement.application.services;




import com.librarymanagement.librarymanagement.application.common.interfaces.IPatronRepository;
import com.librarymanagement.librarymanagement.domain.Patron;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    private final IPatronRepository patronRepository;

    public PatronService(IPatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public Either<String, List<Patron>> getAllPatrons() {
        List<Patron> patrons = patronRepository.findAll();
        return patrons.isEmpty() ? Either.left("No patrons found") : Either.right(patrons);
    }

    public Either<String, Patron> getPatronById(Long id) {
        Optional<Patron> patron = patronRepository.findById(id);
        return patron.map(Either::<String, Patron>right).orElseGet(() -> Either.left("Patron not found"));
    }

    public Either<String, Patron> addPatron(Patron patron) {
        if (patronRepository.existsByContactInfo(patron.getContactInfo())) {
            return Either.left("A patron with this contact info already exists");
        }
        Patron savedPatron = patronRepository.save(patron);
        return Either.right(savedPatron);
    }

    public Either<String, Patron> updatePatron(Long id, Patron patronDetails) {
        return patronRepository.findById(id)
                .map(patron -> {
                    patron.setName(patronDetails.getName());
                    patron.setContactInfo(patronDetails.getContactInfo());
                    patronRepository.save(patron);
                    return Either.<String, Patron>right(patron);
                })
                .orElseGet(() -> Either.left("Patron not found"));
    }

    public Either<String, String> deletePatron(Long id) {
        if (!patronRepository.existsById(id)) {
            return Either.left("Patron not found");
        }
        patronRepository.deleteById(id);
        return Either.right("Patron deleted successfully");
    }
}

