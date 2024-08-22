package com.modak.notification_service.domain.notificationregistry

import com.modak.notification_service.domain.entity.Notification
import com.modak.notification_service.domain.service.exception.NotificationLimitExceededException
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UpdateNotificationValidator : NotificationValidator {

    override fun validateLimit(notifications: List<Notification>) {
        val now = LocalDateTime.now()

        notifications.count {
            it.createdAt.year == now.year &&
            it.createdAt.month == now.month &&
            it.createdAt.dayOfMonth == now.dayOfMonth &&
            it.createdAt.hour == now.hour &&
            it.createdAt.minute == now.minute
        }.let {
            if (it >= UPDATE_SENDING_LIMIT) throw NotificationLimitExceededException()
        }
    }

    companion object {
        private const val UPDATE_SENDING_LIMIT = 2
    }
}
