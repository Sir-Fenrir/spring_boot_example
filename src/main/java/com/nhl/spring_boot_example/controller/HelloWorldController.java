package com.nhl.spring_boot_example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Deze klasse bevat methodes die worden aangeroepen als de juiste URL
 * wordt aangeroepen.
 * De annotatie @RestController zorgt ervoor dat Spring deze klasse tijdens het
 * scannen van de classpath opmerkt en voor jou gaat instantiëren.
 * Je hoeft hier dus niet zélf een instantie van te maken!
 */
@RestController
public class HelloWorldController {

    private final String greeting;

    /**
     * Via dependency injection wordt de property 'greeting.default' uit de application.properties
     * gehaald en hier meegegeven aan de constructor. Spring roept deze constructor voor je aan.
     * @param greeting
     */
    public HelloWorldController(@Value("${greeting.default}") String greeting) {
        this.greeting = greeting;
    }


    @GetMapping("/hello")
    public String helloWorld() {
        return greeting;
    }

}




