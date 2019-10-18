package com.scholanova.projectdb.repositories;

import com.scholanova.projectdb.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MessageRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Message> listAll() {
        String query = "SELECT ID as id, " +
                "CONTENT AS content " +
                "FROM MESSAGES";

        Map<String, Object> parameters = new HashMap<>();

        return jdbcTemplate.query(query,
                parameters,
                new BeanPropertyRowMapper<>(Message.class));
    }

    public Message getById(Integer id) {
        String query = "SELECT ID as id, " +
                "CONTENT AS content " +
                "FROM MESSAGES " +
                "WHERE ID = :id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        return jdbcTemplate.query(query,
                parameters,
                new BeanPropertyRowMapper<>(Message.class))
                .stream()
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public Integer create(Message message) {
        KeyHolder holder = new GeneratedKeyHolder();

        String query = "INSERT INTO MESSAGES " +
                "(CONTENT) VALUES " +
                "(:content)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("content", message.getContent());

        jdbcTemplate.update(query, parameters, holder);

        return (Integer) holder.getKeys().get("ID");
    }
}
