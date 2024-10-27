package ru.mai.coursework.controller.http.booking.dto

import ru.mai.coursework.entity.BookingStatus
import java.time.LocalDateTime

/**
 * DTO for {@link ru.mai.coursework.entity.Booking}
 */
data class GetBookingResponse(
    val bookingId: Int,
    val user: String,
    val timeSlotId: Int,
    val bookingTime: LocalDateTime,
    val status: BookingStatus
)