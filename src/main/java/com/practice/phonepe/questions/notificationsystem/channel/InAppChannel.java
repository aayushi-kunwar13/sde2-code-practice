package com.practice.phonepe.questions.notificationsystem.channel;

import com.practice.phonepe.questions.notificationsystem.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystem.entities.Notification;
import com.practice.phonepe.questions.notificationsystem.exception.NotificationDeliveryException;

public class InAppChannel implements NotificationChannel {

    @Override
    public void send(Notification notification) throws NotificationDeliveryException {
        System.out.printf("[IN_APP] user=%s title=\"%s\" body=\"%s\"%n",
                notification.getRecipient().getId(), notification.getTitle(), notification.getMessage());
    }

    @Override
    public ChannelType getType() {
        return ChannelType.IN_APP;
    }
}
