package ru.mai.coursework.entity

import java.sql.ResultSet
import java.time.LocalDateTime

data class Payment(
    val paymentId: Int,
    val bookingId: Int,
    val amount: Float,
    val paymentTime: LocalDateTime = LocalDateTime.now(),
    val paymentMethod: String?,
    val status: PaymentStatus = PaymentStatus.PENDING,
)

fun ResultSet.toPayment(): Payment =
    Payment(
        paymentId = getInt("payment_id"),
        bookingId = getInt("booking_id"),
        amount = getFloat("amount"),
        paymentTime = getTimestamp("payment_time").toLocalDateTime(),
        paymentMethod = getString("payment_method"),
        status = PaymentStatus.valueOf(getString("status").uppercase()),
    )
