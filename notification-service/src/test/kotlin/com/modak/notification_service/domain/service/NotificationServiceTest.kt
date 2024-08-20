package com.modak.notification_service.domain.service

import com.modak.notification_service.domain.entity.Notification
import com.modak.notification_service.domain.entity.enum.NotificationType
import com.modak.notification_service.domain.gateway.NotificationGateway
import com.modak.notification_service.domain.repository.NotificationRepository
import com.modak.notification_service.domain.service.exception.NotificationLimitExceededException
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class NotificationServiceTest {

    private val repository: NotificationRepository = mockk()
    private val gateway: NotificationGateway = mockk()

    private val service = NotificationService(repository, gateway)

    @Test
    fun `given a Notification, when type is UPDATE and limit has not exceeded, should send successfully`() {
        val notification = Notification(
            userId = "123",
            content = "UPDATE message",
            type = NotificationType.UPDATE
        )
        val otherNotification = Notification(
            userId = "123",
            content = "UPDATE message 2",
            type = NotificationType.UPDATE,
            createdAt = LocalDateTime.now().minusMinutes(1)
        )

        every {
            repository.findByUserIdAndType(notification.userId, notification.type)
        } returns listOf(notification, otherNotification)
        every { gateway.send(notification) } returns true
        every { repository.save(notification) } returns notification

        service.send(notification)

        verify {
            gateway.send(notification)
            repository.save(notification)
        }
    }

    @Test
    fun `given a Notification, when type is UPDATE and limit has exceeded, should throw Exception`() {
        val notification = Notification(
            userId = "123",
            content = "UPDATE message",
            type = NotificationType.UPDATE
        )

        every {
            repository.findByUserIdAndType(notification.userId, notification.type)
        } returns listOf(notification, notification)

        assertThrows<NotificationLimitExceededException> {
            service.send(notification)
        }
    }

    @Test
    fun `given a Notification, when type is NEWS and limit has not exceeded, should send successfully`() {
        val notification = Notification(
            userId = "123",
            content = "NEWS message",
            type = NotificationType.NEWS,
            createdAt = LocalDateTime.now().minusDays(1)
        )

        every {
            repository.findByUserIdAndType(notification.userId, notification.type)
        } returns listOf(notification)
        every { gateway.send(notification) } returns true
        every { repository.save(notification) } returns notification

        service.send(notification)

        verify {
            gateway.send(notification)
            repository.save(notification)
        }
    }

    @Test
    fun `given a Notification, when type is NEWS and limit has exceeded, should throw Exception`() {
        val notification = Notification(
            userId = "123",
            content = "NEWS message",
            type = NotificationType.NEWS
        )

        every {
            repository.findByUserIdAndType(notification.userId, notification.type)
        } returns listOf(notification)

        assertThrows<NotificationLimitExceededException> {
            service.send(notification)
        }
    }
}