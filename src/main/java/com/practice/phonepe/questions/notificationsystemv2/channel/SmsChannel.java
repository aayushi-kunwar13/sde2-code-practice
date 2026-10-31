package com.practice.phonepe.questions.notificationsystemv2.channel;

import com.practice.phonepe.questions.notificationsystemv2.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystemv2.entities.Notification;
import com.practice.phonepe.questions.notificationsystemv2.entities.User;

public class SmsChannel implements NotificationChannel {
    @Override
    public void send(User user, Notification notification) {
        System.out.println("[SMS] to=" + user.getPhone() + " : " + notification.getMessage());
    }

    @Override
    public ChannelType getType() {
        return ChannelType.SMS;
    }
}
