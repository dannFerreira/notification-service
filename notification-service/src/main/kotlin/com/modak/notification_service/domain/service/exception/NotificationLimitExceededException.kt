package com.modak.notification_service.domain.service.exception

class NotificationLimitExceededException(
    val type: String = "LIMIT_EXCEEDED",
    val reason: String = "Notification limit exceeded"
) : Exception(reason)
