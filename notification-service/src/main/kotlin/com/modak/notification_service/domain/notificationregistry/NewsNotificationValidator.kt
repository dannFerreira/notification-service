package com.modak.notification_service.domain.notificationregistry

import com.modak.notification_service.domain.entity.Notification
import com.modak.notification_service.domain.service.exception.NotificationLimitExceededException
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class NewsNotificationValidator : NotificationValidator {

    override fun validateLimit(notifications: List<Notification>) {
        notifications.filter { it.createdAt.toLocalDate() == LocalDate.now() }.let {
            if (it.size >= NEWS_SENDING_LIMIT) throw NotificationLimitExceededException()
        }
    }

    companion object {
        private const val NEWS_SENDING_LIMIT = 1
    }
}
