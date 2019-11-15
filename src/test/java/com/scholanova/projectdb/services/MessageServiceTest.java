package com.scholanova.projectdb.services;

import com.scholanova.projectdb.exceptions.MessageCannotBeEmptyException;
import com.scholanova.projectdb.exceptions.MessageCannotBeOver50CharException;
import com.scholanova.projectdb.models.Message;
import com.scholanova.projectdb.repositories.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    private MessageService messageService;

    @Mock
    private MessageRepository messageRepository;

    @BeforeEach
    void setUp() {
        messageService = new MessageService(messageRepository);
    }

    @Test
    void givenNoContentMessage_whenCreated_failsWithNoEmptyMessageError() {
        // GIVEN
        Message emptyMessage = new Message(null, null, null);

        // WHEN
        assertThrows(MessageCannotBeEmptyException.class, () -> {
            messageService.create(emptyMessage);
        });

        // THEN
        verify(messageRepository, never()).create(emptyMessage);
    }

    @Test
    void givenEmptyContentMessage_whenCreated_failsWithNoEmptyMessageError() {
        // GIVEN
        Message emptyMessage = new Message(null, "  ", "");

        // WHEN
        assertThrows(MessageCannotBeEmptyException.class, () -> {
            messageService.create(emptyMessage);
        });

        // THEN
        verify(messageRepository, never()).create(emptyMessage);
    }

    @Test
    void givenTooLongContentMessage_whenCreated_failsWithTooLongContentError() {
        // GIVEN
        char[] charArrayOf50Chars = new char[50];
        Arrays.fill(charArrayOf50Chars, 'a');
        String tooLongContent = String.valueOf(charArrayOf50Chars);
        Message tooLongContentMessage = new Message(null, tooLongContent, "title");

        // WHEN
        assertThrows(MessageCannotBeOver50CharException.class, () -> {
            messageService.create(tooLongContentMessage);
        });

        // THEN
        verify(messageRepository, never()).create(tooLongContentMessage);
    }

    @Test
    void givenNotEmptyContentMessage_whenCreated_savesMessageInRepository() throws Exception {
        // GIVEN
        Message notEmptyMessage = new Message(null, " a content ", "");
        Message savedMessage = new Message(1, " a content ", "");
        Integer messageId = 1;

        when(messageRepository.create(notEmptyMessage)).thenReturn(messageId);
        when(messageRepository.getById(messageId)).thenReturn(savedMessage);

        // WHEN
        Message returnedMessage = messageService.create(notEmptyMessage);

        // THEN
        verify(messageRepository, atLeastOnce()).create(notEmptyMessage);
        verify(messageRepository, atLeastOnce()).getById(messageId);

        assertThat(returnedMessage).isEqualTo(savedMessage);
    }
}