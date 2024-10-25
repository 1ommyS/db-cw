package ru.mai.coursework.controller.http.timeSlots.dto

import java.time.LocalDateTime

class GetAvailableTimeSlotsResponse(
    private val availableTimeSlots: List<Dto>
) {
    class Dto(
        val id: Int,
        val escapeRoomId: Int,
        val escapeRoomName: String,
        val startTime: LocalDateTime,
        val endTime: LocalDateTime,
    )

}