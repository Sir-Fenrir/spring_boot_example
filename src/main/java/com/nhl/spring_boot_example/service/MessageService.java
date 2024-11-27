package com.nhl.spring_boot_example.service;

import com.nhl.spring_boot_example.model.Message;
import com.nhl.spring_boot_example.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    public Message getMessage(long id) {
        return messageRepository.findById(id) // Dit returnt een Optional.
                .orElseThrow(() -> new RuntimeException("No Message found with id " + id));
    }

    public List<Message> findByTitle(String title) {
        return messageRepository.findByTitleContaining(title);
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
}
