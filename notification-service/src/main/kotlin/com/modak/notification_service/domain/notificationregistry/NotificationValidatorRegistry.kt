package com.modak.notification_service.domain.notificationregistry

import com.modak.notification_service.domain.entity.enum.NotificationType
import org.springframework.stereotype.Component

@Component
class NotificationValidatorRegistry(
    private val updateNotificationValidator: UpdateNotificationValidator,
    private val newsNotificationValidator: NewsNotificationValidator,
    private val marketingNotificationValidator: MarketingNotificationValidator
) {

    fun getValidator(type: NotificationType): NotificationValidator {
        return when(type) {
            NotificationType.UPDATE -> updateNotificationValidator
            NotificationType.NEWS -> newsNotificationValidator
            NotificationType.MARKETING -> marketingNotificationValidator
        }
    }
}
