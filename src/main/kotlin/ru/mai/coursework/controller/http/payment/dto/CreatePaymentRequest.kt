package ru.mai.coursework.controller.http.payment.dto

import ru.mai.coursework.entity.Payment
import ru.mai.coursework.entity.PaymentStatus
import java.time.LocalDateTime

data class CreatePaymentRequest(
    val bookingId: Int,
    val amount: Float,
) {
    suspend fun toEntity(): Payment =
        Payment(
            bookingId = bookingId,
            amount = amount,
            paymentMethod = null,
            status = PaymentStatus.PENDING,
            paymentTime = LocalDateTime.now(),
            paymentId = 0,
        )
}
