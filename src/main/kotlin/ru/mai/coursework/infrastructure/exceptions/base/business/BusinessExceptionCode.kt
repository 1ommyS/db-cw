package ru.mai.coursework.infrastructure.exceptions.base.business

enum class BusinessExceptionCode(
    val value: String
) {

    USER_NOT_FOUND("Пользователь не найден"),
    ACCESS_DENIED("Доступ запрещен"),
    ;
}