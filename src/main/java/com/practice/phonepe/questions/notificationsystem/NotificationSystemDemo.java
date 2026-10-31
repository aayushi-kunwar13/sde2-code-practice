package com.practice.phonepe.questions.notificationsystem;

import com.practice.phonepe.questions.notificationsystem.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystem.entities.Notification;
import com.practice.phonepe.questions.notificationsystem.entities.Priority;
import com.practice.phonepe.questions.notificationsystem.entities.User;
import com.practice.phonepe.questions.notificationsystem.observer.LoggingNotificationObserver;
import com.practice.phonepe.questions.notificationsystem.service.NotificationService;

import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class NotificationSystemDemo {

    public static void main(String[] args) throws InterruptedException {
        NotificationService service = NotificationService.getInstance();
        service.subscribe(new LoggingNotificationObserver());

        User alice = new User("u1", "Alice", "alice@example.com", "+1-555-0100", "device-token-abc",
                EnumSet.of(ChannelType.EMAIL, ChannelType.SMS, ChannelType.PUSH));

        User bob = new User("u2", "Bob", "bob@example.com", null, null,
                EnumSet.of(ChannelType.IN_APP));

        Notification orderShipped = Notification.builder(alice, "Your order #4821 has shipped!")
                .title("Order Update")
                .priority(Priority.HIGH)
                .metadata("orderId", "4821")
                .build();
        service.send(orderShipped);

        Notification promo = Notification.builder(bob, "20% off your next purchase")
                .title("Weekend Sale")
                .priority(Priority.LOW)
                .build();
        // Requests EMAIL + IN_APP, but Bob only prefers IN_APP -> only IN_APP is sent.
        service.send(promo, Set.of(ChannelType.EMAIL, ChannelType.IN_APP));

        service.shutdown();
        service.awaitTerminationQuietly(5, TimeUnit.SECONDS);
    }
}
