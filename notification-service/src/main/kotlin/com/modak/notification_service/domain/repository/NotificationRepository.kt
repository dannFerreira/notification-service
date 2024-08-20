package com.modak.notification_service.domain.repository

import com.modak.notification_service.domain.entity.Notification
import com.modak.notification_service.domain.entity.enum.NotificationType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : JpaRepository<Notification, String> {

    fun findByUserIdAndType(userId: String, type: NotificationType): List<Notification>
}
