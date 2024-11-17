package ru.mai.coursework.infrastructure.exceptions.base.invocation

enum class InvocationExceptionCode(
    val value: String
) {

    USER_NOT_FOUND("Ошибка при поиске пользователя в db.users"),
    BOOKING_ERROR("Ошибка при оформлении бронирования"),
    GET_ROLE_ERROR("Ошибка при получении роли")
    ;

}