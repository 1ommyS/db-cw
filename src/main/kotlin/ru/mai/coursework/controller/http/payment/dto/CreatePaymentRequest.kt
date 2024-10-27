package ru.mai.coursework.controller.http.payment.dto

data class CreatePaymentRequest(
    val bookingId: Int,
    val amount: Float
)
