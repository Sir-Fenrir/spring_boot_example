package com.nhl.spring_boot_example.dto;

public class MessageDTO {

    private String title;

    private String content;

    private Long authorId;

    public MessageDTO() {
    }

    public MessageDTO(String title, String content, long authorId) {
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

    public Long getAuthor() {
        return authorId;
    }

    public void setAuthor(Long authorId) {
        this.authorId = authorId;
    }
}
