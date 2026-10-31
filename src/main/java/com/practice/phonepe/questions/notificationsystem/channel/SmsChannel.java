package com.practice.phonepe.questions.notificationsystem.channel;

import com.practice.phonepe.questions.notificationsystem.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystem.entities.Notification;
import com.practice.phonepe.questions.notificationsystem.exception.NotificationDeliveryException;

import java.util.concurrent.ThreadLocalRandom;

public class SmsChannel implements NotificationChannel {

    @Override
    public void send(Notification notification) throws NotificationDeliveryException {
        String phone = notification.getRecipient().getPhoneNumber();
        if (phone == null || phone.isBlank()) {
            throw new NotificationDeliveryException("User has no phone number on file");
        }
        if (ThreadLocalRandom.current().nextInt(100) < 20) {
            throw new NotificationDeliveryException("SMS gateway rejected message to " + phone);
        }
        System.out.printf("[SMS] to=%s body=\"%s\"%n", phone, notification.getMessage());
    }

    @Override
    public ChannelType getType() {
        return ChannelType.SMS;
    }
}
