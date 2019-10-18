package com.scholanova.projectdb.controllers;

import com.scholanova.projectdb.models.Message;
import com.scholanova.projectdb.services.MessageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
            Message message1 = new Message(1, "Message 1");
            Message message2 = new Message(2, "Message 2");
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

    @Test
    void createMessage() {
    }
}