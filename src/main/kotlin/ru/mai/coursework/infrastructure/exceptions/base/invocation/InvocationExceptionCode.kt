package ru.mai.coursework.infrastructure.exceptions.base.invocation

enum class InvocationExceptionCode(
    val value: String
) {

    USER_NOT_FOUND("Ошибка при поиске пользователя в db.users");
}