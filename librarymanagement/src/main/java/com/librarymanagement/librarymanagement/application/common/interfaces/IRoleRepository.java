package com.librarymanagement.librarymanagement.application.common.interfaces;

import com.librarymanagement.librarymanagement.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

    Optional<List<Role>> findByName(String name);
}



