package com.scholanova.projectdb.services;

import com.scholanova.projectdb.models.Message;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MessageService {

    public Message create(Message message) {
        return null;
    }

    public List<Message> listAll() {
        Message message1 = new Message(1, "Hello World");
        Message message2 = new Message(2, "Hello Planet");
        return Arrays.asList(message1, message2);
    }
}
