package com.nhl.spring_boot_example.mapper;

import com.nhl.spring_boot_example.dto.AuthorDTO;
import com.nhl.spring_boot_example.model.Author;
import com.nhl.spring_boot_example.model.Message;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Om makkelijk te switchen tussen Entities en DTO's maken we mappers.
 */
@Component
public class AuthorMapper implements Mapper<AuthorDTO, Author> {

    @Override
    public AuthorDTO toDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        // We zetten de messages om naar IDs, zodat we dus geen oneindige loops krijgen in onze JSON.
        List<Long> messageIds = author.getMessages()
                .stream()
                .map(Message::getId)
                .toList();
        dto.setMessages(messageIds);

        return dto;
    }

    @Override
    public List<AuthorDTO> toDTO(List<Author> authors) {
        return authors.stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public Author toEntity(AuthorDTO authorDTO) {
        return new Author(authorDTO.getName());
    }
}
