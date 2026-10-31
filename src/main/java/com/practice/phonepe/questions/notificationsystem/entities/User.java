package com.practice.phonepe.questions.notificationsystem.entities;

import java.util.EnumSet;
import java.util.Set;

public class User {

    private final String id;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final String deviceToken;
    private final Set<ChannelType> preferredChannels;

    public User(String id, String name, String email, String phoneNumber, String deviceToken,
                Set<ChannelType> preferredChannels) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.deviceToken = deviceToken;
        this.preferredChannels = preferredChannels == null || preferredChannels.isEmpty()
                ? EnumSet.noneOf(ChannelType.class)
                : EnumSet.copyOf(preferredChannels);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public Set<ChannelType> getPreferredChannels() {
        return preferredChannels;
    }
}
