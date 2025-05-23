package ru.mai.coursework.infrastructure.exceptions.base

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessException
import ru.mai.coursework.infrastructure.exceptions.base.invocation.InvocationException
import ru.mai.coursework.infrastructure.exceptions.base.validation.ValidationException

@ControllerAdvice
class ErrorHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(BusinessException::class)
    suspend fun handleBusinessException(ex: BusinessException): ResponseEntity<String?> =
        ResponseEntity<String?>(ex.message, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(InvocationException::class)
    suspend fun handleInvocationException(ex: InvocationException): ResponseEntity<String?> =
        ResponseEntity<String?>(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)

    @ExceptionHandler(ValidationException::class)
    suspend fun handleInvocationException(ex: ValidationException): ResponseEntity<String?> =
        ResponseEntity<String?>(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)
}
