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
            insertMessage("MESSAGE 1");
            insertMessage("MESSAGE 2");

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

    @Test
    void create() {
    }

    private void insertMessage(String text) {
        jdbcTemplate.execute(
                String.format("INSERT INTO MESSAGES " +
                                "(CONTENT) " +
                                "VALUES ('%s')",
                        text)
        );
    }
}