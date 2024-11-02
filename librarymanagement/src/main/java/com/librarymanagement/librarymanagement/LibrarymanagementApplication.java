package com.librarymanagement.librarymanagement;

import com.librarymanagement.librarymanagement.application.common.interfaces.IBookRepository;
import com.librarymanagement.librarymanagement.application.common.interfaces.IBorrowingRecordRepository;
import com.librarymanagement.librarymanagement.application.common.interfaces.IPatronRepository;
import com.librarymanagement.librarymanagement.application.common.interfaces.IRoleRepository;
import com.librarymanagement.librarymanagement.domain.Book;
import com.librarymanagement.librarymanagement.domain.BorrowingRecord;
import com.librarymanagement.librarymanagement.domain.Patron;
import com.librarymanagement.librarymanagement.domain.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@RestController
@SpringBootApplication
public class LibrarymanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarymanagementApplication.class, args);
	}

	@GetMapping()
 	public String SayHello () {
		   return "Hello";
	}



	@Bean
	CommandLineRunner commandLineRunner (IRoleRepository roleRepository)
	{
		return args ->{


			try{

				var roles = roleRepository.findByName("User");
				if (roles.isEmpty())
					System.out.println("cant fetch");

				else {
					String roleName = roles.get().get(0).getName();
					System.out.println(roleName);
				}
			}catch (Exception ex) {

				System.out.println(ex.getMessage());
			}
		};
	}

}

