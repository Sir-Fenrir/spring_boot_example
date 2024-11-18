package com.nhl.spring_boot_example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Nu hebben we een serieuze configuratie klasse! We gebruiken @EnableWebSocketMessageBroker om Spring
 * te vertellen dat er WebSocket onderdelen opgezet moeten worden. Ook implementeren we een interface die het makkelijker
 * maakt om het te configureren.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * In deze methode registreren we een hele simpele message broker (waar een websocket mee werkt), met de naam '/topic'.
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
    }

    /**
     * Hier registreren we het endpoint wat aangeroepen moet worden om een websocket connectie te starten.
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").setAllowedOrigins("*");
    }
}
