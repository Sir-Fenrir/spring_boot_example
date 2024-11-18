package com.nhl.spring_boot_example.tasks;

import com.nhl.spring_boot_example.model.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * Een @Component kan je zien als een generieke Spring Bean. Deze wordt ook opgepikt via classpath scanning en geïnstantieerd.
 */
@Component
public class TimerTask {

    // Dit is de WebSocket messaging klasse, die laten we injecteren door Spring. Kijk maar bij de constructor.
    private final SimpMessagingTemplate template;

    // Wat denk je dat dit doet?
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public TimerTask(SimpMessagingTemplate template) {
        this.template = template;
    }

    /**
     * Met de @Scheduled annotatie zeggen tegen Spring dat de methode timeSender elke 5 seconden aan moet roepen.
     * Dit kan je vrij uitgebreid instellen. Je zou zelfs ervoor kunnen zorgen dat het alleen elke woensdag
     * om 5 uur 's nachts draait. Experimenteer er eens mee! Bij twijfel, kijk naar de documentatie van @Scheduled.
     * Die kan je via je IDE downloaden, maar je kan ook via een zoekmachine naar keuze kijken.
     * Als je niet weet of het van Java zelf of van Spring is, kijk dan eens naar het import statement bovenin deze klasse.
     */
    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void timeSender() {
        Message message = new Message("Current Time", LocalDateTime.now().format(formatter));
        template.convertAndSend("/topic/messages", message);
    }

}
