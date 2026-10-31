package com.practice.phonepe.questions.notificationsystem.channel;

import com.practice.phonepe.questions.notificationsystem.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystem.entities.Notification;
import com.practice.phonepe.questions.notificationsystem.exception.NotificationDeliveryException;

import java.util.concurrent.ThreadLocalRandom;

public class PushChannel implements NotificationChannel {

    @Override
    public void send(Notification notification) throws NotificationDeliveryException {
        String deviceToken = notification.getRecipient().getDeviceToken();
        if (deviceToken == null || deviceToken.isBlank()) {
            throw new NotificationDeliveryException("User has no registered device");
        }
        if (ThreadLocalRandom.current().nextInt(100) < 20) {
            throw new NotificationDeliveryException("Push provider unreachable for device " + deviceToken);
        }
        System.out.printf("[PUSH] device=%s title=\"%s\" body=\"%s\"%n",
                deviceToken, notification.getTitle(), notification.getMessage());
    }

    @Override
    public ChannelType getType() {
        return ChannelType.PUSH;
    }
}
