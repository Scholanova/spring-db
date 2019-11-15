package com.scholanova.projectdb.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scholanova.projectdb.exceptions.MessageCannotBeEmptyException;
import com.scholanova.projectdb.models.Message;
import com.scholanova.projectdb.services.MessageService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Nested
    class Test_listMessage {

        @Test
        void whenNoMessages_thenReturnsEmptyList() throws Exception {
            // Given
            when(messageService.listAll()).thenReturn(Arrays.asList());

            // Then
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("messages", hasSize(0)))
                    .andExpect(view().name("message-list"))
                    .andDo(print());
        }

        @Test
        void whenTwoMessages_thenReturnsListWithTwoMessages() throws Exception {
            // Given
            Message message1 = new Message(1, "Message 1", "");
            Message message2 = new Message(2, "Message 2", "");
            when(messageService.listAll()).thenReturn(Arrays.asList(message1, message2));

            // Then
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("messages", hasSize(2)))
                    .andExpect(model().attribute("messages", containsInAnyOrder(message1, message2)))
                    .andExpect(view().name("message-list"))
                    .andDo(print());
        }
    }

    @Test
    void listMessage() {
    }

    @Test
    void newMessage() {
    }

    @Nested
    class Test_createMessage {

        @Test
        void whenValidMessage_thenReturnsListMessageScreenWithNewMessage() throws Exception {
            // Given
            ArgumentCaptor<Message> argument = ArgumentCaptor.forClass(Message.class);

            String newMessageContent = "Message 2";
            Message existingMessage = new Message(1, "Message 1", "");
            Message messageToCreate = new Message(null, newMessageContent, "");
            Message messageCreated = new Message(2, newMessageContent, "");
            when(messageService.create(any(Message.class))).thenReturn(messageCreated);
            when(messageService.listAll()).thenReturn(Arrays.asList(existingMessage, messageCreated));

            String jsonBody = new ObjectMapper().writeValueAsString(messageToCreate);

            // Then
            mockMvc.perform(post("/").param("content", newMessageContent))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("messages", hasSize(2)))
                    .andExpect(model().attribute("messages", containsInAnyOrder(existingMessage, messageCreated)))
                    .andExpect(model().attribute("newMessage", is(messageCreated)))
                    .andExpect(view().name("message-list"));

            verify(messageService, atLeastOnce()).create(argument.capture());
            assertThat(argument.getValue()).extracting(Message::getContent).isEqualTo(newMessageContent);
        }

        @Test
        void whenEmptyContentMessage_thenReturnsToFormScreenWithEmptyContentErrorMessage() throws Exception {
            // Given
            ArgumentCaptor<Message> argument = ArgumentCaptor.forClass(Message.class);

            String newMessageEmptyContent = "  ";
            String errorMessage = "Message cannot be empty !";

            Message messageToCreate = new Message(null, newMessageEmptyContent, "");

            when(messageService.create(any(Message.class))).thenThrow(new MessageCannotBeEmptyException());

            // Then
            mockMvc.perform(post("/").param("content", newMessageEmptyContent))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute(
                            "message",
                            hasProperty("content", is(newMessageEmptyContent))
                    ))
                    .andExpect(model().attribute("errorMessage", errorMessage))
                    .andExpect(view().name("message-new"));

            verify(messageService, atLeastOnce()).create(argument.capture());
            assertThat(argument.getValue()).extracting(Message::getContent).isEqualTo(newMessageEmptyContent);
        }
    }
}