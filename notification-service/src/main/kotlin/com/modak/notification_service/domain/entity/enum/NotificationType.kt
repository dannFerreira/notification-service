package com.modak.notification_service.domain.entity.enum

enum class NotificationType(val sendingLimit: Int) {
    UPDATE(2),
    NEWS(1),
    MARKETING(3)
}
