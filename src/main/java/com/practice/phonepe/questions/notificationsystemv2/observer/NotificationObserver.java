package com.practice.phonepe.questions.notificationsystemv2.observer;

import com.practice.phonepe.questions.notificationsystemv2.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystemv2.entities.User;

public interface NotificationObserver {
    void onResult(User user, ChannelType type, boolean success, String detail);
}
