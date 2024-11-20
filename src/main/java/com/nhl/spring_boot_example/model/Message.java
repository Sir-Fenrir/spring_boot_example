package com.nhl.spring_boot_example.model;

import jakarta.persistence.*;

/**
 * We hebben hier nu een entity van gemaakt met de annotatie @Entity.
 * Dit zegt tegen ons ORM (Hibernate, automatisch geïmporteerd en geconfigureerd door Spring Boot),
 * dat er een tabel is in de database die hier bij hoort.
 * De naam van die tabel wordt afgeleid van de naam van de klasse. Als die afwijkt, moet je de tabelnaam
 * meegeven via @Table.
 */
@Entity
@Table(name = "messages")
public class Message {

    // Een tabel heeft een primary key. Die mappen we met de annotatie @Id.
    // We moeten Hibernate ook vertellen hoe de primary key wordt bepaald.
    // In ons scenario wordt die automatisch gegenereerd door de database.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Als een field naam overeenkomt met de naam van een kolom, hoef je niets te doen.
    // Deze zal automatisch gemapped worden.
    // Mocht de naam anders zijn, kan je dat ook aan @Column toevoegen.
    // We geven nu aan dat een veld niet null mag zijn. Deze regel staat ook in de database.
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    // We definiëren hier een relatie met de Author klasse.
    // De @ManyToOne zegt dat er Many Messages (deze klasse) zijn voor One Author (de andere klasse).
    // De name is de naam van de kolom met de foreign key.
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
