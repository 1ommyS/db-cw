package ru.mai.coursework.entity

import java.sql.ResultSet
import java.time.LocalDateTime

data class TimeSlot(
    val timeSlotId: Int,
    val escapeRoomId: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val isAvailable: Boolean
)

fun ResultSet.toTimeSlot(): TimeSlot {
    return TimeSlot(
        timeSlotId = getInt("time_slot_id"),
        escapeRoomId = getInt("escape_room_id"),
        startTime = getTimestamp("start_time").toLocalDateTime(),
        endTime = getTimestamp("end_time").toLocalDateTime(),
        isAvailable = getBoolean("is_available")
    )
}