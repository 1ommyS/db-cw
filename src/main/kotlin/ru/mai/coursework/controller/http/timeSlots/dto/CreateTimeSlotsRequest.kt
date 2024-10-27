package ru.mai.coursework.controller.http.timeSlots.dto

import java.time.LocalDateTime

data class CreateTimeSlotsRequest(
    val data: Data
) {
    data class Data(
        val escapeRoomId: Int,
        val busyTimes: List<BusyTime>
    )

    data class BusyTime(
        val startTime: LocalDateTime,
        val endTime: LocalDateTime
    )
}