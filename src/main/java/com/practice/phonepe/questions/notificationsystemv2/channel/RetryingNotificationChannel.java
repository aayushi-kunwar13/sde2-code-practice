package com.practice.phonepe.questions.notificationsystemv2.channel;

import com.practice.phonepe.questions.notificationsystemv2.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystemv2.entities.Notification;
import com.practice.phonepe.questions.notificationsystemv2.entities.User;

/**
 * Wraps any channel and retries on failure. Keeps retry logic out of every
 * individual channel implementation - a channel just knows how to send once.
 */
public class RetryingNotificationChannel implements NotificationChannel {
    private final NotificationChannel delegate;
    private final int maxAttempts;

    public RetryingNotificationChannel(NotificationChannel delegate, int maxAttempts) {
        this.delegate = delegate;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public void send(User user, Notification notification) {
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                delegate.send(user, notification);
                return;
            } catch (RuntimeException e) {
                System.out.println("[RETRY] " + delegate.getType() + " attempt " + attempt + "/" + maxAttempts
                        + " failed: " + e.getMessage());
                if (attempt == maxAttempts) {
                    throw e;
                }
            }
        }
    }

    @Override
    public ChannelType getType() {
        return delegate.getType();
    }
}
