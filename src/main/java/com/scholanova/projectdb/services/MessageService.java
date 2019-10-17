package com.scholanova.projectdb.services;

import com.scholanova.projectdb.models.Message;
import com.scholanova.projectdb.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message create(Message message) {
        Integer newId = messageRepository.create(message);
        return messageRepository.getById(newId);
    }

    public List<Message> listAll() {
        return messageRepository.listAll();
    }
}
