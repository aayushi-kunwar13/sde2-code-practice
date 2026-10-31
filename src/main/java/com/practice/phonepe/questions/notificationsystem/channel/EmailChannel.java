package com.practice.phonepe.questions.notificationsystem.channel;

import com.practice.phonepe.questions.notificationsystem.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystem.entities.Notification;
import com.practice.phonepe.questions.notificationsystem.exception.NotificationDeliveryException;

import java.util.concurrent.ThreadLocalRandom;

public class EmailChannel implements NotificationChannel {

    @Override
    public void send(Notification notification) throws NotificationDeliveryException {
        String email = notification.getRecipient().getEmail();
        if (email == null || email.isBlank()) {
            throw new NotificationDeliveryException("User has no email on file");
        }
        if (ThreadLocalRandom.current().nextInt(100) < 20) {
            throw new NotificationDeliveryException("SMTP gateway timeout for " + email);
        }
        System.out.printf("[EMAIL] to=%s subject=\"%s\" body=\"%s\"%n",
                email, notification.getTitle(), notification.getMessage());
    }

    @Override
    public ChannelType getType() {
        return ChannelType.EMAIL;
    }
}
