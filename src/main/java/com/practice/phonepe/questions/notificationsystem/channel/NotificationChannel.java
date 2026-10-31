package com.practice.phonepe.questions.notificationsystem.channel;

import com.practice.phonepe.questions.notificationsystem.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystem.entities.Notification;
import com.practice.phonepe.questions.notificationsystem.exception.NotificationDeliveryException;

/**
 * Strategy interface: one implementation per delivery channel (Email, SMS, Push, In-App).
 * New channels are added by implementing this interface + registering in the factory —
 * no existing code changes (Open/Closed).
 */
public interface NotificationChannel {

    void send(Notification notification) throws NotificationDeliveryException;

    ChannelType getType();
}
