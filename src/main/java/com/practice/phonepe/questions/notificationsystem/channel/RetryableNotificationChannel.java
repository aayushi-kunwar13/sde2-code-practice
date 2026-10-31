package com.practice.phonepe.questions.notificationsystem.channel;

import com.practice.phonepe.questions.notificationsystem.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystem.entities.Notification;
import com.practice.phonepe.questions.notificationsystem.exception.NotificationDeliveryException;

/**
 * Decorator: adds retry-with-backoff around any {@link NotificationChannel}
 * without the underlying channel implementation knowing about retries.
 */
public class RetryableNotificationChannel implements NotificationChannel {

    private final NotificationChannel delegate;
    private final int maxAttempts;
    private final long initialBackoffMillis;

    public RetryableNotificationChannel(NotificationChannel delegate, int maxAttempts, long initialBackoffMillis) {
        this.delegate = delegate;
        this.maxAttempts = maxAttempts;
        this.initialBackoffMillis = initialBackoffMillis;
    }

    @Override
    public void send(Notification notification) throws NotificationDeliveryException {
        long backoff = initialBackoffMillis;
        NotificationDeliveryException lastFailure = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                delegate.send(notification);
                return;
            } catch (NotificationDeliveryException e) {
                lastFailure = e;
                System.out.printf("[RETRY] %s attempt %d/%d failed for notification %s: %s%n",
                        delegate.getType(), attempt, maxAttempts, notification.getId(), e.getMessage());
                if (attempt < maxAttempts) {
                    sleep(backoff);
                    backoff *= 2;
                }
            }
        }
        throw new NotificationDeliveryException(
                "Delivery failed via " + delegate.getType() + " after " + maxAttempts + " attempts", lastFailure);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public ChannelType getType() {
        return delegate.getType();
    }
}
