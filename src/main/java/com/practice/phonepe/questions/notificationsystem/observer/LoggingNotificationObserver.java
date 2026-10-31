package com.practice.phonepe.questions.notificationsystem.observer;

import com.practice.phonepe.questions.notificationsystem.entities.NotificationStatus;

public class LoggingNotificationObserver implements NotificationObserver {

    @Override
    public void onDeliveryResult(DeliveryResult result) {
        if (result.getStatus() == NotificationStatus.SENT) {
            System.out.printf("[AUDIT] notification=%s channel=%s status=SENT%n",
                    result.getNotification().getId(), result.getChannel());
        } else {
            System.out.printf("[AUDIT] notification=%s channel=%s status=FAILED reason=%s%n",
                    result.getNotification().getId(), result.getChannel(), result.getFailureReason());
        }
    }
}
