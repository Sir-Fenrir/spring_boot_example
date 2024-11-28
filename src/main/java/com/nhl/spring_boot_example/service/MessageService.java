package com.nhl.spring_boot_example.service;

import com.nhl.spring_boot_example.model.Message;
import com.nhl.spring_boot_example.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Met @Service zeggen we tegen Spring Boot dat er ook een instantie gemaakt moet worden van de MessageService.
 * Spring instantiëert dit dan en stopt het resulterende object in de ApplicationContext.
 * <p>
 * Een Service klasse is bedoeld voor business logica. Oftewel, bijna alles wat niet gaat over communiceren met
 * de client (dat doen de RestControllers immers) of de databronnen (daarvoor hebben we Repositories).
 * <p>
 * Nu is deze klasse nog vrij leeg, dus is het voornamelijk een doorgeefluik.
 * Echter, in de praktijk wordt een applicatie natuurlijk altijd complexer dan deze nu is.
 */
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
