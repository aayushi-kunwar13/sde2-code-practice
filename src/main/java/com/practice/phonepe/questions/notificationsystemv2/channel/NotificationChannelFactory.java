package com.practice.phonepe.questions.notificationsystemv2.channel;

import com.practice.phonepe.questions.notificationsystemv2.entities.ChannelType;

import java.util.Map;

public class NotificationChannelFactory {
    private final Map<ChannelType, NotificationChannel> channels;

    public NotificationChannelFactory(Map<ChannelType, NotificationChannel> channels) {
        this.channels = channels;
    }

    public NotificationChannel get(ChannelType type) {
        NotificationChannel channel = channels.get(type);
        if (channel == null) {
            throw new IllegalArgumentException("No channel registered for " + type);
        }
        return channel;
    }
}
