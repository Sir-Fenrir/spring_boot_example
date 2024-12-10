package com.nhl.spring_boot_example.controller;

import com.nhl.spring_boot_example.mapper.MessageMapper;
import com.nhl.spring_boot_example.model.Author;
import com.nhl.spring_boot_example.model.Message;
import com.nhl.spring_boot_example.repository.AuthorRepository;
import com.nhl.spring_boot_example.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * We zeggen hier tegen Spring Boot & JUnit (die werken hier samen) dat we de MessageController willen testen.
 * De @WebMvcTest annotatie tuigt dan een soort mini Spring Boot webserver op met alleen de MessageController.
 * <p>
 * Bevat deze testklasse nu unit tests of integratietests? We testen alleen de logica in MessageController,
 * maar deze is wel geïntegreerd met Spring Boot, en je zult straks zien dat we die integratie testen.
 * Technisch gezien zijn het dus integratie testen. Al noemen sommigen het ook wel 'slice testen'.
 */
@WebMvcTest(controllers = MessageController.class)
@Import(MessageMapper.class) // De MessageMapper mocken is niet echt nodig, dus vragen we op deze manier aan Spring om die wel te instantiëren.
class MessageControllerTest {

    // Eerst zagen we alleen Dependency Injection via de constructor. Het kan echter ook op velden.
    // Dit is voor productiecode niet zo handig, omdat het lastiger te testen is.
    // Echter, nu zitten wel al in een test, dus dat maakt niet echt uit.
    // Later zien we waar dit object goed voor is.
    @Autowired
    private MockMvc mockMvc;

    // Deze annotatie zegt tegen Spring dat we een mock versie willen van de MessageService.
    // Net als we in periode 1 hebben gezien met Mockito!
    // Die wordt dan aangemaakt en in dit veld geïnjecteerd. Daarnaast wordt de mock versie ook automatisch
    // in de MessageController geïnjecteerd via de constructor.
    @MockBean
    private MessageService messageService;

    // Dit is ook een dependency van de MessageController. Daar maken we ook een mock voor.
    @MockBean
    private SimpMessagingTemplate simpleMessagingTemplate;

    // Dit is geen dependency van de MessageController, maar wél van de MessageMapper.
    // De AuthorRepository werkt met de database, dus deze mocken we ook.
    @MockBean
    private AuthorRepository authorRepository;

    // Voor elke test wordt deze methode uitgevoerd.
    @BeforeEach
    public void setup() {
        // We maken even een nep-autor
        Author author = new Author();
        author.setName("Jan");
        // Nu stellen we in dat we een lijst van messages teruggeven als messageService.getMessages() wordt aaangeroepen.
        when(messageService.getMessages())
                .thenReturn(
                        List.of(
                                new Message("First", "Content", author),
                                new Message("Second", "Content", author),
                                new Message("Fourth", "Content", author)
                        )
                );

        // En dan nog een instelling voor het zoek van een author met ID 1.
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
    }

    @Test
    void getOneMessages() throws Exception {
        // Arrange
        // We zetten wat nep data klaar
        var author = new Author("Test");
        author.setId(1L);
        var message = new Message("Title", "Content", author);
        message.setId(2L);

        // We stellen de MessageService mock in
        when(messageService.getMessage(anyLong()))
                .thenReturn(message);

        // Act!
        // We roepen nu een URL aan met de mockMvc. We hadden ook gewoon messageController.getMessage aan kunnen roepen,
        // máár, dan testen we niet of de annotaties goed zijn ingesteld.
        this.mockMvc.perform(get("/messages/1"))

                // We chainen verder op de act, en kommen nu bij de Assert fase.
                // Eerst kijken we of de statuscode klopt.
                .andExpect(status().isOk())
                // En dan of de inhoud voldoet aan onze verwachtingen.
                .andExpect(content().json("{\"id\":2,\"title\":\"Title\",\"content\":\"Content\",\"authorId\":1}"));
    }

    @Test
    public void newMessageTest() throws Exception {
        // Arrange
        // De message die we op willen slaan
        Message result = new Message("Title", "Content", new Author("Test"));
        result.setId(1L);
        // Nog steeds Arrange: wat er moet gebeuren als saveMessage wordt aangeroepen.
        when(messageService.saveMessage(any())).thenReturn(result);

        // Act! Voor het HTTP request uit.
        mockMvc.perform(
                MockMvcRequestBuilders.post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"authorId\":1,\"content\":\"Content\", \"title\":\"Title\"}")
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        // Verifiëer dat de messageService.saveMessage methode is aangeroepen. Elk argument telt.
        // Je kan ook specifiekere matches meegeven!
        verify(messageService, times(1)).saveMessage(any());
    }
}
