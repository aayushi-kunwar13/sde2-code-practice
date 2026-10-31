package com.practice.phonepe.questions.bookmyshowv2.entities;

public class User {
    private final String id;
    private final String name;
    private final String emailId;

    public User(String id, String name, String emailId) {
        this.id = id;
        this.name = name;
        this.emailId = emailId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }
}
