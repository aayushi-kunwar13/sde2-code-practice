package com.practice.phonepe.questions.notificationsystemv2.observer;

import com.practice.phonepe.questions.notificationsystemv2.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystemv2.entities.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The Subject. Owns nothing but the observer list - dispatch logic stays in
 * NotificationService, so adding a new observer never touches it.
 */
public class NotificationEventPublisher {
    private final List<NotificationObserver> observers = new CopyOnWriteArrayList<>();

    public void subscribe(NotificationObserver observer) {
        observers.add(observer);
    }

    public void publish(User user, ChannelType type, boolean success, String detail) {
        for (NotificationObserver observer : observers) {
            observer.onResult(user, type, success, detail);
        }
    }
}
