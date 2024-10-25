package ru.mai.coursework.controller.http.timeSlots.dto

import java.time.LocalDateTime

data class GetAvailableTimeSlotsRequest(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val escapeRoomId: Int
)
