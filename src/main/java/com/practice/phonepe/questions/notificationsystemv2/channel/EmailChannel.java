package com.practice.phonepe.questions.notificationsystemv2.channel;

import com.practice.phonepe.questions.notificationsystemv2.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystemv2.entities.Notification;
import com.practice.phonepe.questions.notificationsystemv2.entities.User;

public class EmailChannel implements NotificationChannel {
    @Override
    public void send(User user, Notification notification) {
        if (java.util.concurrent.ThreadLocalRandom.current().nextInt(100) < 40) {
            throw new RuntimeException("SMTP gateway timeout for " + user.getEmail());
        }
        System.out.println("[EMAIL] to=" + user.getEmail() + " subject=\"" + notification.getTitle()
                + "\" : " + notification.getMessage());
    }

    @Override
    public ChannelType getType() {
        return ChannelType.EMAIL;
    }
}
