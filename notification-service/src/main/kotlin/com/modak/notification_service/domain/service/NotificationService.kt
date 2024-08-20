package com.modak.notification_service.domain.service

import com.modak.notification_service.domain.entity.Notification
import com.modak.notification_service.domain.entity.enum.NotificationType
import com.modak.notification_service.domain.entity.enum.NotificationType.*
import com.modak.notification_service.domain.gateway.NotificationGateway
import com.modak.notification_service.domain.repository.NotificationRepository
import com.modak.notification_service.domain.service.exception.NotificationLimitExceededException
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val notificationGateway: NotificationGateway
) {

    fun send(notification: Notification) {
        val notifications = notificationRepository.findByUserIdAndType(notification.userId, notification.type)

        validateNotificationLimit(notifications, notification.type)

        notificationGateway.send(notification)
        notificationRepository.save(notification)
    }

    private fun validateNotificationLimit(notifications: List<Notification>, type: NotificationType) {
        when (type) {
            UPDATE -> {
                val now = LocalDateTime.now()

                notifications.count {
                    it.createdAt.year == now.year &&
                            it.createdAt.month == now.month &&
                            it.createdAt.dayOfMonth == now.dayOfMonth &&
                            it.createdAt.hour == now.hour &&
                            it.createdAt.minute == now.minute
                }.let {
                    if (it >= UPDATE.sendingLimit) throw NotificationLimitExceededException()
                }
            }

            NEWS -> {
                notifications.filter { it.createdAt.toLocalDate() == LocalDate.now() }.let {
                    if (it.size >= NEWS.sendingLimit) throw NotificationLimitExceededException()
                }
            }

            MARKETING -> {
                val now = LocalDateTime.now()

                notifications.count {
                    it.createdAt.year == now.year &&
                            it.createdAt.month == now.month &&
                            it.createdAt.dayOfMonth == now.dayOfMonth &&
                            it.createdAt.hour == now.hour
                }.let {
                    if (it >= MARKETING.sendingLimit) throw NotificationLimitExceededException()
                }
            }
        }
    }
}
