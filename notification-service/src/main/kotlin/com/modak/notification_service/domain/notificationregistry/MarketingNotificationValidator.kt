package com.modak.notification_service.domain.notificationregistry

import com.modak.notification_service.domain.entity.Notification
import com.modak.notification_service.domain.service.exception.NotificationLimitExceededException
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MarketingNotificationValidator : NotificationValidator {

    override fun validateLimit(notifications: List<Notification>) {
        val now = LocalDateTime.now()

        notifications.count {
            it.createdAt.year == now.year &&
                    it.createdAt.month == now.month &&
                    it.createdAt.dayOfMonth == now.dayOfMonth &&
                    it.createdAt.hour == now.hour
        }.let {
            if (it >= MARKETING_SENDING_LIMIT) throw NotificationLimitExceededException()
        }
    }

    companion object {
        private const val MARKETING_SENDING_LIMIT = 3
    }
}
