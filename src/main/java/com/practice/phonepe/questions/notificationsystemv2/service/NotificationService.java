package com.practice.phonepe.questions.notificationsystemv2.service;

import com.practice.phonepe.questions.notificationsystemv2.channel.NotificationChannelFactory;
import com.practice.phonepe.questions.notificationsystemv2.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystemv2.entities.Notification;
import com.practice.phonepe.questions.notificationsystemv2.entities.NotificationStatus;
import com.practice.phonepe.questions.notificationsystemv2.entities.User;
import com.practice.phonepe.questions.notificationsystemv2.observer.NotificationEventPublisher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NotificationService {
    private final NotificationChannelFactory factory;
    private final NotificationEventPublisher publisher;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private final Map<String, Map<ChannelType, NotificationStatus>> statusByNotification = new ConcurrentHashMap<>();

    public NotificationService(NotificationChannelFactory factory, NotificationEventPublisher publisher) {
        this.factory = factory;
        this.publisher = publisher;
    }

    public void notifyUser(User user, Notification notification) {
        for (ChannelType type : user.getPreferredChannels()) {
            executor.submit(() -> {
                try {
                    factory.get(type).send(user, notification);
                    recordStatus(notification, type, NotificationStatus.SENT);
                    publisher.publish(user, type, true, null);
                } catch (RuntimeException e) {
                    recordStatus(notification, type, NotificationStatus.FAILED);
                    publisher.publish(user, type, false, e.getMessage());
                }
            });
        }
    }

    private void recordStatus(Notification notification, ChannelType type, NotificationStatus status) {
        statusByNotification
                .computeIfAbsent(notification.getId(), id -> new ConcurrentHashMap<>())
                .put(type, status);
    }

    /** Per-channel outcome for a given notification - each channel gets its own slot. */
    public Map<ChannelType, NotificationStatus> getStatus(String notificationId) {
        return statusByNotification.getOrDefault(notificationId, Map.of());
    }

    public void shutdown() {
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
