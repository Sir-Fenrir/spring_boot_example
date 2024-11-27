package com.nhl.spring_boot_example.service;

import com.nhl.spring_boot_example.model.Author;
import com.nhl.spring_boot_example.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author getAuthor(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Author found with id: " + id));
    }

    public List<Author> getAuthor(String name) {
        return authorRepository.findByName(name);
    }

}
