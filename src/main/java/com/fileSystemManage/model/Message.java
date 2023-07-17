package com.fileSystemManage.model;

import java.util.Objects;

public class Message {
    private Integer statue;
    private String path;
    private String description;
    private String jsonstr;
    public Message(){}
    public Message(Integer statue,String path,String description){
        this.statue = statue;
        this.path = path;
        this.description = description;
    }
    public Message(Integer statue){
        this.statue = statue;
    }

    public Message(Integer statue, String path, String description, String jsonstr) {
        this.statue = statue;
        this.path = path;
        this.description = description;
        this.jsonstr = jsonstr;
    }

    public String getJsonstr() {
        return jsonstr;
    }

    public void setJsonstr(String jsonstr) {
        this.jsonstr = jsonstr;
    }

    public Integer getStatue() {
        return statue;
    }

    public void setStatue(Integer statue) {
        this.statue = statue;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(statue, message.statue) && Objects.equals(path, message.path) && Objects.equals(description, message.description) && Objects.equals(jsonstr, message.jsonstr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statue, path, description, jsonstr);
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
