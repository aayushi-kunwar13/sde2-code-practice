package com.practice.phonepe.questions.notificationsystemv2.entities;

import java.util.Set;

public class User {
    private final String id;
    private final String name;
    private final String email;
    private final String phone;
    private final Set<ChannelType> preferredChannels;

    public User(String id, String name, String email, String phone, Set<ChannelType> preferredChannels) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.preferredChannels = preferredChannels;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public Set<ChannelType> getPreferredChannels() { return preferredChannels; }
}
