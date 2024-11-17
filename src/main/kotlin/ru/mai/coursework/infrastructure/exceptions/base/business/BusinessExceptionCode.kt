package ru.mai.coursework.infrastructure.exceptions.base.business

enum class BusinessExceptionCode(
    val value: String
) {

    USER_NOT_FOUND("Пользователь не найден"),
    USER_ALREADY_EXISTS("Пользователь уже существует"),
    ROLE_NOT_FOUND("Роль не найдена"),
    BOOKING_ERROR("Ошибка при бронировании комнаты"),
    BOOKING_NOT_FOUND("Бронь не найдена"),
    PAYMENT_NOT_FOUND("Платеж не найден"),
    BOOKED_NOT_BY_USER("Ошибка при отмене бронирования: бронирование оформлено другим пользователем"),
    ;
}