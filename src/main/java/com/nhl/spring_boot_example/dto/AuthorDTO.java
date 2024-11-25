package com.nhl.spring_boot_example.dto;

import java.util.List;

public class AuthorDTO {

    private Long id;

    private String name;

    private List<Long> messages;

    public AuthorDTO() {
    }

    public AuthorDTO(Long id, String name, List<Long> messages) {
        this.id = id;
        this.name = name;
        this.messages = messages;
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

    public List<Long> getMessages() {
        return messages;
    }

    public void setMessages(List<Long> messages) {
        this.messages = messages;
    }
}

