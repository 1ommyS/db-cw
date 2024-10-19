package ru.mai.coursework.infrastructure.exceptions.base.validation

enum class ValidationExceptionCode(
    val value: String
) {

    ACCESS_DENIED("Доступ запрещен"),
    ;
}