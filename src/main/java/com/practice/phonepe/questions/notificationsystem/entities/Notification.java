package com.practice.phonepe.questions.notificationsystem.entities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Notification {

    private final String id;
    private final User recipient;
    private final String title;
    private final String message;
    private final Priority priority;
    private final Map<String, String> metadata;

    private Notification(Builder builder) {
        this.id = UUID.randomUUID().toString();
        this.recipient = builder.recipient;
        this.title = builder.title;
        this.message = builder.message;
        this.priority = builder.priority;
        this.metadata = Collections.unmodifiableMap(new HashMap<>(builder.metadata));
    }

    public String getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Priority getPriority() {
        return priority;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public static Builder builder(User recipient, String message) {
        return new Builder(recipient, message);
    }

    public static class Builder {
        private final User recipient;
        private final String message;
        private String title = "";
        private Priority priority = Priority.MEDIUM;
        private final Map<String, String> metadata = new HashMap<>();

        private Builder(User recipient, String message) {
            if (recipient == null) {
                throw new IllegalArgumentException("recipient is required");
            }
            if (message == null || message.isBlank()) {
                throw new IllegalArgumentException("message is required");
            }
            this.recipient = recipient;
            this.message = message;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder metadata(String key, String value) {
            this.metadata.put(key, value);
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }
}
