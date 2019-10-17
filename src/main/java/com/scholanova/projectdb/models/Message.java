package com.scholanova.projectdb.models;

public class Message {

    private Integer id;
    private String content;

    public Message() {
    }

    public Message(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
