package com.nhl.spring_boot_example.repository;

import com.nhl.spring_boot_example.model.Message;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Deze houden we nog even heel simpel!
 * {@literal @}Repository wordt ook automatisch opgepakt door Spring en geïnstantieerd.
 */
@Repository
public class MessageRepository {

    private final List<Message> messages = new ArrayList<>();

    public MessageRepository() {
        this.messages.add(new Message("First", "Some information"));
    }

    public Message getMessage(int id) {
        return this.messages.get(id);
    }

}


