package com.nhl.spring_boot_example.controller;

import com.nhl.spring_boot_example.dto.AuthorDTO;
import com.nhl.spring_boot_example.mapper.AuthorMapper;
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

    private final AuthorMapper authorMapper;

    /**
     * Spring doet dus de dependency injection voor ons. Die roept deze constructor aan
     * en geeft de AuthorRepository instantie er aan mee.
     *
     * @param authorRepository
     */
    public AuthorController(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    /**
     * Geef alle auteurs terug. Of, zoek op naam met een Query parameter!
     *
     * @param name Optioneel, de naam waar op gezocht moet worden.
     * @return Alle auteurs, of alle auteurs met de opgegeven naam.
     */
    @GetMapping
    public List<AuthorDTO> findAll(@RequestParam(defaultValue = "") String name) {
        if ("".equals(name)) {
            return authorMapper.toDTO(authorRepository.findAll());
        }

        return authorMapper.toDTO(authorRepository.findByName(name));
    }
}
