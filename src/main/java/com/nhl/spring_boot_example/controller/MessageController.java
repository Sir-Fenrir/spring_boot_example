package com.nhl.spring_boot_example.controller;

import com.nhl.spring_boot_example.dto.MessageDTO;
import com.nhl.spring_boot_example.mapper.MessageMapper;
import com.nhl.spring_boot_example.model.Message;
import com.nhl.spring_boot_example.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The @RequestMapping annotatie zorgt ervoor dat elke Mapping annotatie in deze klasse
 * 'messages' als basis heeft.
 */
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate template;
    private final MessageMapper mapper;

    public MessageController(SimpMessagingTemplate template, MessageService messageService, MessageMapper mapper) {
        this.template = template;
        this.messageService = messageService;
        this.mapper = mapper;
    }

    /**
     * Deze methode wordt aangeroepen als de url /messages/{id} wordt aangeroepen, via HTTP GET.
     *
     * @param id Deze wordt door Spring uit de URL gehaald en meegegeven als argument.
     * @return
     */
    @GetMapping("/{id}")
    public MessageDTO getMessage(@PathVariable("id") long id) {
        Message message = messageService.getMessage(id);
        return mapper.toDTO(message);
    }

    /**
     * Deze methode wordt aangeroepen als de url /messages wordt aangeroepen, via HTTP GET.
     * <p>
     * De query wordt doorgegeven aan de repository om mee te gaan zoeken.
     *
     * @param query Deze waarde is standaard leeg (zie defaultValue), maar als er een query parameter is,
     *              wordt die meegegeven. Bijvoorbeeld: /messages?query=hello, dan geeft Spring de String 'hello'
     *              mee aan het argument 'query'.
     * @return De gevonden berichten.
     */
    @GetMapping
    public List<MessageDTO> searchMessages(@RequestParam(value = "query", defaultValue = "") String query) {
        return mapper.toDTO(messageService.findByTitle(query));
    }

    /**
     * Je kan ook HTTP POST gebruiken, dan kan je data opsturen.
     * Met de annotatie @ResponseStatus geven we aan dat Spring een andere HTTP status code
     * moet teruggeven dan de standaard 200 OK. Nu gaat die 201 CREATED teruggeven.
     * <p>
     * Nu gaat een binnengekomen bericht ook naar de websocket topic!
     * <p>
     * Een opgestuurd bericht wordt opgeslagen in de database.
     *
     * @param message
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message createMessage(@RequestBody MessageDTO message) {
        template.convertAndSend("/topic/messages", message);
        return messageService.saveMessage(mapper.toEntity(message));
    }

    /**
     * Deze methode luistert op de URL ws://localhost:8080/messages/broadcast (de @RequestMapping op regel 19 wordt
     * hier niet aan toegevoegd), oftewel, luister naar websocket berichten.
     *
     * @param message
     * @return De return value wordt dankzij @SendTo automatisch naar een bepaald topic gestuurd (in dit geval "topic/messages")
     * Alles wat luistert op deze topic wordt automatisch op de hoogte gesteld.
     */
    @MessageMapping("/messages/broadcast")
    @SendTo("/topic/messages")
    public MessageDTO broadcastMessage(@RequestBody MessageDTO message) {
        return message;
    }

}
