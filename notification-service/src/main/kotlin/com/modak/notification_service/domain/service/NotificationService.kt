package com.modak.notification_service.domain.service

import com.modak.notification_service.domain.entity.Notification
import com.modak.notification_service.domain.gateway.NotificationGateway
import com.modak.notification_service.domain.notificationregistry.NotificationValidatorRegistry
import com.modak.notification_service.domain.repository.NotificationRepository
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val notificationGateway: NotificationGateway,
    private val notificationValidatorRegistry: NotificationValidatorRegistry
) {

    fun send(notification: Notification) {
        val notifications = notificationRepository.findByUserIdAndType(notification.userId, notification.type)

        notificationValidatorRegistry.getValidator(notification.type).validateLimit(notifications)

        notificationGateway.send(notification)
        notificationRepository.save(notification)
    }
}
