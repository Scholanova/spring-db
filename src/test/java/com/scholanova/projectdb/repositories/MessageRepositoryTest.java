package com.scholanova.projectdb.repositories;

import com.scholanova.projectdb.models.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(MessageRepository.class)
@JdbcTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void cleanUp() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "MESSAGES");
    }

    @Nested
    class Test_listAll {

        @Test
        void whenNoMessages_thenReturnsEmptyList() {
            // Given

            // When
            List<Message> messages = messageRepository.listAll();

            // Then
            assertThat(messages).isEmpty();
        }

        @Test
        void whenTwoMessages_thenReturnsListWithTwoMessages() {
            // Given
            insertMessage("MESSAGE 1", "TITLE 1");
            insertMessage("MESSAGE 2", "TITLE 2");

            // When
            List<Message> messages = messageRepository.listAll();

            // Then
            assertThat(messages)
                    .extracting(Message::getContent)
                    .containsOnly("MESSAGE 1", "MESSAGE 2");
        }
    }

    @Test
    void getById() {
    }

    @Nested
    class Test_create {

        @Test
        void whenCreateMessage_thenMessageIsInDatabase() {
            // Given
            Message messageToCreate = new Message(null, "Content 1", "Title 1");

            // When
            Integer id = messageRepository.create(messageToCreate);

            // Then
            Message createdMessage = messageRepository.getById(id);

            assertThat(createdMessage).extracting(Message::getContent).isEqualTo("Content 1");
            assertThat(createdMessage).extracting(Message::getTitle).isEqualTo("Title 1");
            assertThat(createdMessage).extracting(Message::getId).isEqualTo(id);
        }
    }

    private void insertMessage(String content, String title) {
        String query = "INSERT INTO MESSAGES " +
                "(CONTENT, TITLE) " +
                "VALUES ('%s', '%s')";
        jdbcTemplate.execute(String.format(query, content, title));
    }
}