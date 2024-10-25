package ru.mai.coursework.entity

import java.sql.ResultSet
import java.time.LocalDateTime

data class TimeSlot(
    val id: Int,
    val escapeRoomId: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val isAvailable: Boolean
)

fun ResultSet.toTimeSlot(): TimeSlot {
    return TimeSlot(
        id = getInt("id"),
        escapeRoomId = getInt("escape_room_id"),
        startTime = getTimestamp("start_time").toLocalDateTime(),
        endTime = getTimestamp("end_time").toLocalDateTime(),
        isAvailable = getBoolean("is_available")
    )
}