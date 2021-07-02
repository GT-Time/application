package com.example.registration;

public class Announcement {
    private String content;
    private String name;
    private String date;

    public Announcement(String content, String name, String date) {
        this.content = content;
        this.name = name;
        this.date = date;
    }

    public void setContent(String notice) {
        this.content = notice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
