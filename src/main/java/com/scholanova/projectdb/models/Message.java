package com.scholanova.projectdb.models;

public class Message {

    private Integer id;
    private String content;
    private String title;

    public Message() {
    }

    public Message(Integer id, String content, String title) {
        this.id = id;
        this.content = content;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
