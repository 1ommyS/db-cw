package ru.mai.coursework.infrastructure.repository.timeSlots

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.mai.coursework.entity.TimeSlot
import ru.mai.coursework.entity.toTimeSlot

@Repository
class TimeSlotsRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    suspend fun getAvailableTimeSlots(request: TimeSlot): List<TimeSlot> {
        val sql = """
            CALL get_available_timeslots_by_date(?, ?, ?)
        """.trimIndent()

        return jdbcTemplate.query(
            sql,
            { rs, _ -> rs.toTimeSlot() },
            request.escapeRoomId,
            request.startTime,
            request.endTime
        )
    }
}