package com.practice.phonepe.questions.notificationsystemv2.entities;

import java.util.UUID;

public class Notification {
    private final String id;
    private final String title;
    private final String message;

    public Notification(String title, String message) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.message = message;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
}
