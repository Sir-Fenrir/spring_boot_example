package com.nhl.spring_boot_example.model;

public class Message {

    private String title;

    private String content;

    /**
     * Deze lege constructor is nodig! De JSON library die we gebruiken (Jackson) heeft dit nodig om een
     * instantie van de klasse te maken.
     */
    public Message() {
    }

    public Message(String title, String content) {
        this.title = title;
        this.content = content;
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

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
