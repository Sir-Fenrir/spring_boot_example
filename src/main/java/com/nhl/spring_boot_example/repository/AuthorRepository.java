package com.nhl.spring_boot_example.repository;

import com.nhl.spring_boot_example.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByName(String name);

}
