package com.practice.phonepe.questions.notificationsystem.service;

import com.practice.phonepe.questions.notificationsystem.channel.NotificationChannel;
import com.practice.phonepe.questions.notificationsystem.channel.NotificationChannelFactory;
import com.practice.phonepe.questions.notificationsystem.channel.RetryableNotificationChannel;
import com.practice.phonepe.questions.notificationsystem.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystem.entities.Notification;
import com.practice.phonepe.questions.notificationsystem.entities.NotificationStatus;
import com.practice.phonepe.questions.notificationsystem.exception.NotificationDeliveryException;
import com.practice.phonepe.questions.notificationsystem.observer.DeliveryResult;
import com.practice.phonepe.questions.notificationsystem.observer.NotificationObserver;
import com.practice.phonepe.questions.notificationsystem.observer.NotificationPublisher;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Orchestrator (Singleton). Resolves the effective set of channels for a notification,
 * dispatches to each one asynchronously (wrapped with retry), and hands the outcome off
 * to {@link NotificationPublisher} (the Observer-pattern Subject) to fan out to observers.
 */
public class NotificationService {

    private static final int MAX_ATTEMPTS = 3;
    private static final long INITIAL_BACKOFF_MILLIS = 200;

    private static volatile NotificationService instance;

    private final NotificationChannelFactory channelFactory;
    private final ExecutorService executor;
    private final NotificationPublisher publisher = new NotificationPublisher();
    private final Map<String, Map<ChannelType, NotificationStatus>> statusByNotification = new ConcurrentHashMap<>();

    private NotificationService() {
        this.channelFactory = new NotificationChannelFactory();
        this.executor = Executors.newFixedThreadPool(4);
    }

    public static NotificationService getInstance() {
        if (instance == null) {
            synchronized (NotificationService.class) {
                if (instance == null) {
                    instance = new NotificationService();
                }
            }
        }
        return instance;
    }

    public void subscribe(NotificationObserver observer) {
        publisher.subscribe(observer);
    }

    public void unsubscribe(NotificationObserver observer) {
        publisher.unsubscribe(observer);
    }

    /** Send using the recipient's own channel preferences. */
    public void send(Notification notification) {
        send(notification, notification.getRecipient().getPreferredChannels());
    }

    /** Send restricted to the intersection of requestedChannels and the recipient's preferences. */
    public void send(Notification notification, Set<ChannelType> requestedChannels) {
        Set<ChannelType> preferred = notification.getRecipient().getPreferredChannels();
        Set<ChannelType> effective = requestedChannels.isEmpty() ? preferred : requestedChannels;

        Map<ChannelType, NotificationStatus> statuses = new EnumMap<>(ChannelType.class);
        for (ChannelType type : effective) {
            if (!preferred.isEmpty() && !preferred.contains(type)) {
                continue;
            }
            statuses.put(type, NotificationStatus.PENDING);
        }
        statusByNotification.put(notification.getId(), statuses);

        for (ChannelType type : statuses.keySet()) {
            dispatchAsync(notification, type);
        }
    }

    private void dispatchAsync(Notification notification, ChannelType type) {
        NotificationChannel channel = new RetryableNotificationChannel(
                channelFactory.get(type), MAX_ATTEMPTS, INITIAL_BACKOFF_MILLIS);

        executor.submit(() -> {
            try {
                channel.send(notification);
                recordResult(notification, type, NotificationStatus.SENT, null);
            } catch (NotificationDeliveryException e) {
                recordResult(notification, type, NotificationStatus.FAILED, e.getMessage());
            }
        });
    }

    private void recordResult(Notification notification, ChannelType type, NotificationStatus status,
                               String failureReason) {
        statusByNotification.get(notification.getId()).put(type, status);
        publisher.publish(new DeliveryResult(notification, type, status, failureReason));
    }

    public Map<ChannelType, NotificationStatus> getStatus(String notificationId) {
        return statusByNotification.getOrDefault(notificationId, Map.of());
    }

    public void shutdown() {
        executor.shutdown();
    }

    public void awaitTerminationQuietly(long timeout, TimeUnit unit) {
        try {
            executor.awaitTermination(timeout, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
