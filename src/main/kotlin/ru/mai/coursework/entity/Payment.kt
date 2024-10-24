package ru.mai.coursework.entity

import java.math.BigDecimal
import java.sql.ResultSet
import java.time.LocalDateTime

data class Payment(
    val paymentId: Int,
    val bookingId: Int,
    val amount: BigDecimal,
    val paymentTime: LocalDateTime,
    val paymentMethod: String?,
    val status: PaymentStatus
)

fun ResultSet.toPayment(): Payment {
    return Payment(
        paymentId = getInt("payment_id"),
        bookingId = getInt("booking_id"),
        amount = getBigDecimal("amount"),
        paymentTime = getTimestamp("payment_time").toLocalDateTime(),
        paymentMethod = getString("payment_method"),
        status = PaymentStatus.valueOf(getString("status").uppercase())
    )
}