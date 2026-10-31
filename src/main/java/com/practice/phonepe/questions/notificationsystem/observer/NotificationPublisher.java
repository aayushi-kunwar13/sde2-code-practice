package com.practice.phonepe.questions.notificationsystem.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The Subject/Observable in the Observer pattern. Holds no dispatch logic of its own —
 * it only maintains the list of interested observers and fans a {@link DeliveryResult}
 * out to each of them. {@code NotificationService} depends on this instead of managing
 * observers itself.
 */
public class NotificationPublisher {

    private final List<NotificationObserver> observers = new CopyOnWriteArrayList<>();

    public void subscribe(NotificationObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(NotificationObserver observer) {
        observers.remove(observer);
    }

    public void publish(DeliveryResult result) {
        for (NotificationObserver observer : observers) {
            observer.onDeliveryResult(result);
        }
    }
}
