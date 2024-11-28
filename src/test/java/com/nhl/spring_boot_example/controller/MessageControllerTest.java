package com.nhl.spring_boot_example.controller;

import com.nhl.spring_boot_example.mapper.MessageMapper;
import com.nhl.spring_boot_example.model.Author;
import com.nhl.spring_boot_example.model.Message;
import com.nhl.spring_boot_example.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
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

    // Dit is ook nog een dependency van de MessageController.
    // Alleen is het hier iets complexer. Op zich is het niet erg als deze niet gemockt wordt,
    // ware het niet dat de MessageMapper weer afhankelijk is van de AuthorRepository.
    // Voor het gemak mocken we die toch wel, maar gaan we straks de mock iets anders instellen.
    @MockBean
    private MessageMapper messageMapper;

    // Voor elke test wordt deze methode uitgevoerd.
    @BeforeEach
    void setup() {
        // Oké, hier stellen we nu in dat als de toDTO methode wordt aangeroepen
        // (Die niet de AuthorRepository gebruikt)
        // dat de echte implementatie wordt aangeroepen i.p.v de mock implementatie.
        when(messageMapper.toDTO(any(Message.class))).thenCallRealMethod();
    }

    @Test
    void getAllMessages() throws Exception {
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
}
