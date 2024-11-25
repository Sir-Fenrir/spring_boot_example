package com.nhl.spring_boot_example.dto;

/**
 * Waarom hebben we nu dit Data Transfer Object (DTO) gemaakt?
 * Dat heeft een aantal redenen:
 * <p>
 * 1. Mogelijk heeft de database velden die je niet wilt laten zien aan clients (zoals wachtwoorden).
 * 2. De datastructuur kan anders zijn in de database dan dat je wilt laten zien aan clients.
 * 3. Er zitten bidirectionele relaties in je data die niet naar JSON omgezet kunnen worden.
 * <p>
 * Om problemen met JSON serialization te voorkomen krijgt stoppen we alleen de ID van de Author in de JSON.
 */
public class MessageDTO {

    private Long id;

    private String title;

    private String content;

    private Long authorId;

    public MessageDTO() {
    }

    public MessageDTO(Long id, String title, String content, long authorId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
