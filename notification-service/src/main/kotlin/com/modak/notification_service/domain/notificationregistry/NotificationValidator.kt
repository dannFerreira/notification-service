package com.modak.notification_service.domain.notificationregistry

import com.modak.notification_service.domain.entity.Notification

interface NotificationValidator {

    fun validateLimit(notifications: List<Notification>)
}
