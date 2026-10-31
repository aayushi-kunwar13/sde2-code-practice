package com.practice.phonepe.questions.notificationsystemv2.channel;

import com.practice.phonepe.questions.notificationsystemv2.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystemv2.entities.Notification;
import com.practice.phonepe.questions.notificationsystemv2.entities.User;

public interface NotificationChannel {
    void send(User user, Notification notification);
    ChannelType getType();
}
