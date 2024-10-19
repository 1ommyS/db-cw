package ru.mai.coursework.infrastructure.exceptions.base.validation

class ValidationException(
    val code: ValidationExceptionCode,
    override val cause: Throwable? = null,
) : RuntimeException(code.value, cause) {

}