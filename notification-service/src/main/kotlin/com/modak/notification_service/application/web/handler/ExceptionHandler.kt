package com.modak.notification_service.application.web.handler

import com.modak.notification_service.domain.service.exception.NotificationLimitExceededException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(NotificationLimitExceededException::class)
    fun handleNotificationException(
        exception: NotificationLimitExceededException
    ): ResponseEntity<ApiError> {
        val error = ApiError(exception.type, exception.reason)
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<String> {
        return ResponseEntity("${ex.message}", HttpStatus.BAD_REQUEST)
    }
}
