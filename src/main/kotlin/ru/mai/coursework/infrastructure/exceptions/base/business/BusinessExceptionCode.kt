package ru.mai.coursework.infrastructure.exceptions.base.business

enum class BusinessExceptionCode(
    val value: String
) {

    USER_NOT_FOUND("Пользователь не найден"),
    USER_ALREADY_EXISTS("Пользователь уже существует"),
    ROLE_NOT_FOUND("Роль не найдена")
    ;
}