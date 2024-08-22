package com.modak.notification_service.domain.notificationregistry

import com.modak.notification_service.domain.entity.enum.NotificationType
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class NotificationValidatorRegistryTest {

    private val updateNotificationValidator: UpdateNotificationValidator = mockk()
    private val newsNotificationValidator: NewsNotificationValidator = mockk()
    private val marketingNotificationValidator: MarketingNotificationValidator = mockk()

    private val notificationValidatorRegistry = NotificationValidatorRegistry(
        updateNotificationValidator,
        newsNotificationValidator,
        marketingNotificationValidator
    )

    @Test
    fun `given UPDATE type, should return its Validator successfully`() {
        val response = notificationValidatorRegistry.getValidator(NotificationType.UPDATE)

        assertIs<UpdateNotificationValidator>(response)
    }

    @Test
    fun `given NEWS type, should return its Validator successfully`() {
        val response = notificationValidatorRegistry.getValidator(NotificationType.NEWS)

        assertIs<NewsNotificationValidator>(response)
    }

    @Test
    fun `given MARKETING type, should return its Validator successfully`() {
        val response = notificationValidatorRegistry.getValidator(NotificationType.MARKETING)

        assertIs<MarketingNotificationValidator>(response)
    }
}
