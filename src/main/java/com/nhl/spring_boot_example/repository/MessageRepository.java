package com.nhl.spring_boot_example.repository;

import com.nhl.spring_boot_example.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Dit is nu een interface! Maar een interface doet toch niets?
 * Correct, maar Spring genereert bij het opstarten een implementatie voor deze interface.
 * Spring doet dit omdat we de interface JpaRepository extenden.
 * Daarom is de @Repository annotatie ook niet meer nodig.
 * <p>
 * JpaRepository heeft zelf al methodes gedefiniëerd, die gebruik maken van de generics die we meegeven.
 * Spring gaat deze dus automatisch voor ons implementeren voor Message entity.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Deze methode hebben we zelf gedefiniëerd. Dus wat gaat Spring dan doen?
     * Die gaat de naam van de methode lezen en op basis daarvan een bijpassende query genereren!
     * En de parameters worden daar dan ingeplugd.
     * @param title De titel waar op gezocht moet worden.
     * @return Alle gevonden berichten
     */
    List<Message> findByTitleContaining(String title);

    /**
     * Bij de methode hierboven genereert Spring op basis van de methode naam de implementatie en de juiste query.
     * Je kan ook zelf de query meegeven via de @Query annotatie.
     * Als je de query leest valt het je waarschijnlijk op dat het geen gewone SQL is. Dat klopt.
     * Dit is JPQL, de Java Persistence Query Language. Die verwijst naar de entiteiten,
     * zoals Message, i.p.v. de tabellen.
     * @param title De titel waar op gezocht moet worden.
     * @return Alle gevonden berichten
     */
    @Query("select m from Message m where m.title like concat('%', :title, '%')")
    List<Message> findByTitleViaJPQL(String title);
}


