package com.modak.notification_service.application.web.controller

import com.modak.notification_service.application.web.dto.NotificationRequestDto
import com.modak.notification_service.domain.service.NotificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notification")
class NotificationController(
    private val service: NotificationService
) {

    @PostMapping
    fun send(@RequestBody request: NotificationRequestDto): ResponseEntity<Any> {
        service.send(request.toDomain())
        return ResponseEntity.ok().build()
    }
}
