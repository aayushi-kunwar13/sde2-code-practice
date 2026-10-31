package com.practice.phonepe.questions.notificationsystem.observer;

import com.practice.phonepe.questions.notificationsystem.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystem.entities.Notification;
import com.practice.phonepe.questions.notificationsystem.entities.NotificationStatus;

public class DeliveryResult {

    private final Notification notification;
    private final ChannelType channel;
    private final NotificationStatus status;
    private final String failureReason;

    public DeliveryResult(Notification notification, ChannelType channel, NotificationStatus status,
                           String failureReason) {
        this.notification = notification;
        this.channel = channel;
        this.status = status;
        this.failureReason = failureReason;
    }

    public Notification getNotification() {
        return notification;
    }

    public ChannelType getChannel() {
        return channel;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public String getFailureReason() {
        return failureReason;
    }
}
