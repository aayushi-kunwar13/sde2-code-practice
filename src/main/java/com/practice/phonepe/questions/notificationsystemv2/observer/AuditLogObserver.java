package com.practice.phonepe.questions.notificationsystemv2.observer;

import com.practice.phonepe.questions.notificationsystemv2.entities.ChannelType;
import com.practice.phonepe.questions.notificationsystemv2.entities.User;

public class AuditLogObserver implements NotificationObserver {
    @Override
    public void onResult(User user, ChannelType type, boolean success, String detail) {
        if (success) {
            System.out.println("[AUDIT] user=" + user.getId() + " channel=" + type + " status=SENT");
        } else {
            System.out.println("[AUDIT] user=" + user.getId() + " channel=" + type + " status=FAILED reason=" + detail);
        }
    }
}
