package com.nhl.spring_boot_example.mapper;

import com.nhl.spring_boot_example.dto.MessageDTO;
import com.nhl.spring_boot_example.model.Message;
import com.nhl.spring_boot_example.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageMapper implements Mapper<MessageDTO, Message> {

    private final AuthorRepository authorRepository;

    public MessageMapper(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public MessageDTO toDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getTitle(),
                message.getContent(),
                message.getAuthor().getId()
        );
    }

    @Override
    public List<MessageDTO> toDTO(List<Message> messages) {
        return messages.stream()
                .map(this::toDTO)
                .toList();
    }

    public Message toEntity(MessageDTO messageDTO) {
        return new Message(
                messageDTO.getTitle(),
                messageDTO.getContent(),
                authorRepository.findById(messageDTO.getAuthorId()).orElseThrow()
        );
    }

}
