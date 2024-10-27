package ru.mai.coursework.controller.http.booking.dto

data class CreateBookingRequest(
    val userId: Int,
    val timeSlotId: Int,
    val status: String,
)
