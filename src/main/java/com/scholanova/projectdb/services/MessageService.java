package com.scholanova.projectdb.services;

import com.scholanova.projectdb.exceptions.MessageCannotBeEmptyException;
import com.scholanova.projectdb.models.Message;
import com.scholanova.projectdb.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message create(Message message) throws MessageCannotBeEmptyException {

        if (!messageIsValid(message)) {
            throw new MessageCannotBeEmptyException();
        }

        Integer newId = messageRepository.create(message);
        return messageRepository.getById(newId);
    }

    private boolean messageIsValid(Message message) {
        return message.getContent() != null &&
                message.getContent().trim().length() > 0;
    }

    public List<Message> listAll() {
        return messageRepository.listAll();
    }
}
