# Notification System

**Design a service that sends notifications to users over several channels.**

- Channels: email, SMS, push and in-app; more can be added.
- Each user has preferred channels. Send only on channels that are both requested and preferred.
- Notifications have a title, message, priority and metadata.
- Delivery is asynchronous. Failed sends are retried.
- Other parts of the system can observe delivery results (e.g. logging).

**Design:** singleton service with an executor, Builder for `Notification`, a Factory for channels,
a retry decorator (`RetryableNotificationChannel`) and Observer for delivery results. Run `NotificationSystemDemo`.
