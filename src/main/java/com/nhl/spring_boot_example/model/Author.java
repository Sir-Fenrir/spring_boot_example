package com.nhl.spring_boot_example.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Alles in deze klasse lijkt op wat je hebt gezien bij de Message klasse.
 */
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Nu maken we de relatie bidirectioneel! Oftewel, we kunnen vanuit de Message bij de Author komen en vice versa!
    // De @OneToMany zegt eigenlijk 'One' Author met 'Many' Messages.
    // De 'mappedBy' vertelt wat over de andere kant van de relatie. De string 'author' verwijst naar het veld
    // in Message.
    @OneToMany(mappedBy = "author")
    private List<Message> messages = new ArrayList<>();

    public Author(String name) {
        this.name = name;
    }

    public Author() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
