package com.practice.phonepe.questions.notificationsystem.channel;

import com.practice.phonepe.questions.notificationsystem.entities.ChannelType;

import java.util.EnumMap;
import java.util.Map;

public class NotificationChannelFactory {

    private final Map<ChannelType, NotificationChannel> channels = new EnumMap<>(ChannelType.class);

    public NotificationChannelFactory() {
        register(new EmailChannel());
        register(new SmsChannel());
        register(new PushChannel());
        register(new InAppChannel());
    }

    public final void register(NotificationChannel channel) {
        channels.put(channel.getType(), channel);
    }

    public NotificationChannel get(ChannelType type) {
        NotificationChannel channel = channels.get(type);
        if (channel == null) {
            throw new IllegalArgumentException("No channel registered for type: " + type);
        }
        return channel;
    }
}
