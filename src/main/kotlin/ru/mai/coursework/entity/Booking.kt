package ru.mai.coursework.entity

import java.sql.ResultSet
import java.time.LocalDateTime

data class Booking(
    val bookingId: Int,
    val userId: Int,
    val timeSlotId: Int,
    val bookingTime: LocalDateTime,
    val status: BookingStatus
)

fun ResultSet.toBooking(): Booking {
    return Booking(
        bookingId = getInt("booking_id"),
        userId = getInt("user_id"),
        timeSlotId = getInt("time_slot_id"),
        bookingTime = getTimestamp("booking_time").toLocalDateTime(),
        status = BookingStatus.valueOf(getString("status").uppercase())
    )
}