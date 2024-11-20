package com.nhl.spring_boot_example.controller;

import com.nhl.spring_boot_example.model.Author;
import com.nhl.spring_boot_example.repository.AuthorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("author")
public class AuthorController {

    private final AuthorRepository authorRepository;

    /**
     * Spring doet dus de dependency injection voor ons. Die roept deze constructor aan
     * en geeft de AuthorRepository instantie er aan mee.
     *
     * @param authorRepository
     */
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Geef alle auteurs terug. Of, zoek op naam met een Query parameter!
     *
     * @param name Optioneel, de naam waar op gezocht moet worden.
     * @return Alle auteurs, of alle auteurs met de opgegeven naam.
     */
    @GetMapping
    public List<Author> findAll(@RequestParam(defaultValue = "") String name) {
        if ("".equals(name)) {
            return authorRepository.findAll();
        }

        return authorRepository.findByName(name);
    }
}
