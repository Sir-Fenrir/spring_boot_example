package com.nhl.spring_boot_example.controller;

import com.nhl.spring_boot_example.dto.AuthorDTO;
import com.nhl.spring_boot_example.mapper.AuthorMapper;
import com.nhl.spring_boot_example.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("author")
public class AuthorController {

    private final AuthorService authorService;

    private final AuthorMapper authorMapper;

    /**
     * Spring doet dus de dependency injection voor ons. Die roept deze constructor aan
     * en geeft de AuthorRepository instantie er aan mee.
     */
    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
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
            return authorMapper.toDTO(authorService.findAll());
        }

        return authorMapper.toDTO(authorService.getAuthor(name));
    }

    @GetMapping("/{id}")
    public AuthorDTO findById(@PathVariable("id") long id) {
        return authorMapper.toDTO(authorService.getAuthor(id));
    }
}
