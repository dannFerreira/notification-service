package com.modak.notification_service.domain.entity

import com.modak.notification_service.domain.entity.enum.NotificationType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class Notification(

    @Id
    val id: String = UUID.randomUUID().toString(),

    val userId: String,

    val content: String,

    @Enumerated(EnumType.STRING)
    val type: NotificationType,

    val createdAt: LocalDateTime = LocalDateTime.now()
)
