package com.nhl.spring_boot_example.mapper;

import com.nhl.spring_boot_example.dto.MessageDTO;
import com.nhl.spring_boot_example.model.Message;
import com.nhl.spring_boot_example.repository.AuthorRepository;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    private final AuthorRepository authorRepository;

    public MessageMapper(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public MessageDTO toDTO(Message message) {
        return new MessageDTO(
                message.getTitle(),
                message.getContent(),
                message.getAuthor().getId()
        );
    }

    public Message toEntity(MessageDTO messageDTO) {
        return new Message(
                messageDTO.getTitle(),
                messageDTO.getContent(),
                authorRepository.findById(messageDTO.getAuthor()).orElseThrow()
        );
    }

}
