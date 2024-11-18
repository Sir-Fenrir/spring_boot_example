package com.nhl.spring_boot_example.controller;

import com.nhl.spring_boot_example.model.Message;
import com.nhl.spring_boot_example.repository.MessageRepository;
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

    private final MessageRepository messageReposito;
    private final SimpMessagingTemplate template;

    public MessageController(SimpMessagingTemplate template, MessageRepository messageReposito) {
        this.template = template;
        this.messageReposito = messageReposito;
    }

    /**
     * Deze methode wordt aangeroepen als de url /messages/{id} wordt aangeroepen, via HTTP GET.
     *
     * @param id Deze wordt door Spring uit de URL gehaald en meegegeven als argument.
     * @return
     */
    @GetMapping("/{id}")
    public Message getMessage(@PathVariable("id") long id) {
        return messageReposito.findById(id).orElse(new Message("Error", "Message not found"));
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
    public List<Message> searchMessages(@RequestParam(value = "query", defaultValue = "") String query) {
        return messageReposito.findByTitleContaining(query);
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
    public void createMessage(@RequestBody Message message) {
        messageReposito.save(message);
        template.convertAndSend("/topic/messages", message);
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
    public Message broadcastMessage(@RequestBody Message message) {
        return message;
    }

}
