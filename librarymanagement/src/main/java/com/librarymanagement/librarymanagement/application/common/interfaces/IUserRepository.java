package com.librarymanagement.librarymanagement.application.common.interfaces;


import com.librarymanagement.librarymanagement.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findByEmail(String email);

}
