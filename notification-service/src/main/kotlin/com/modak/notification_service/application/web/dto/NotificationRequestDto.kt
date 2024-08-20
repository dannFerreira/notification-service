package com.modak.notification_service.application.web.dto

import com.modak.notification_service.domain.entity.Notification
import com.modak.notification_service.domain.entity.enum.NotificationType

data class NotificationRequestDto(
    val userId: String,
    val content: String,
    val type: NotificationType
) {
    fun toDomain() = Notification(
        userId = userId,
        content = content,
        type = type
    )
}
