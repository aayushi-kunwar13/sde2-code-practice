package com.practice.phonepe.questions.notificationsystemv2;

import com.practice.phonepe.questions.notificationsystemv2.channel.EmailChannel;
import com.practice.phonepe.questions.notificationsystemv2.channel.NotificationChannelFactory;
import com.practice.phonepe.questions.notificationsystemv2.channel.RetryingNotificationChannel;
import com.practice.phonepe.questions.notificationsystemv2.channel.SmsChannel;
import com.practice.phonepe.questions.notificationsystemv2.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystemv2.entities.Notification;
import com.practice.phonepe.questions.notificationsystemv2.entities.User;
import com.practice.phonepe.questions.notificationsystemv2.observer.AuditLogObserver;
import com.practice.phonepe.questions.notificationsystemv2.observer.NotificationEventPublisher;
import com.practice.phonepe.questions.notificationsystemv2.service.NotificationService;

import java.util.EnumSet;
import java.util.Map;

public class Demo {
    public static void main(String[] args) {
        NotificationChannelFactory factory = new NotificationChannelFactory(Map.of(
                ChannelType.EMAIL, new RetryingNotificationChannel(new EmailChannel(), 3),
                ChannelType.SMS, new SmsChannel()));
        NotificationEventPublisher publisher = new NotificationEventPublisher();
        publisher.subscribe(new AuditLogObserver());
        NotificationService service = new NotificationService(factory, publisher);

        User alice = new User("u1", "Alice", "alice@example.com", "+1-555-0100",
                EnumSet.of(ChannelType.EMAIL, ChannelType.SMS));
        User bob = new User("u2", "Bob", "bob@example.com", "+1-555-0200",
                EnumSet.of(ChannelType.SMS));

        Notification forAlice = new Notification("Order Update", "Your order has shipped!");
        Notification forBob = new Notification("Order Update", "Your order has shipped!");
        service.notifyUser(alice, forAlice);
        service.notifyUser(bob, forBob);

        service.shutdown();
        System.out.println("Alice's notification status per channel: " + service.getStatus(forAlice.getId()));
        System.out.println("Bob's notification status per channel: " + service.getStatus(forBob.getId()));
    }
}
