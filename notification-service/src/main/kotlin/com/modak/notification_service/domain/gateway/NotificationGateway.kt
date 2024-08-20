package com.modak.notification_service.domain.gateway

import com.modak.notification_service.domain.entity.Notification
import org.springframework.stereotype.Component

@Component
class NotificationGateway {

    fun send(notification: Notification): Boolean {
        return true
    }
}
