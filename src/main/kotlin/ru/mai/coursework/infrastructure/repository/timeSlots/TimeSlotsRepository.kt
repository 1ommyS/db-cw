package ru.mai.coursework.infrastructure.repository.timeSlots

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.mai.coursework.controller.http.timeSlots.dto.CreateTimeSlotsRequest
import ru.mai.coursework.entity.TimeSlot
import ru.mai.coursework.entity.toTimeSlot
import ru.mai.coursework.infrastructure.aspects.Log

@Repository
@Log
@Transactional
class TimeSlotsRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {
    suspend fun getAvailableTimeSlots(request: TimeSlot): List<TimeSlot> = withContext(Dispatchers.IO) {
        val sql = """
            CALL get_available_timeslots_by_date(:escapeRoomId, :startDate, :endDate)
        """.trimIndent()

        return@withContext jdbcTemplate.query(
            sql,
            mapOf(
                "escapeRoomId" to request.escapeRoomId,
                "startDate" to request.startTime,
                "endDate" to request.endTime
            )
        ) { rs, _ -> rs.toTimeSlot() }
    }

    suspend fun createTimeSlots(
        timeSlots: CreateTimeSlotsRequest.Data
    ) {
        val sql = """
        INSERT INTO timeslots (escape_room_id, start_time, end_time, is_available) 
        VALUES (:escapeRoomId, :startTime, :endTime, :isAvailable)
    """.trimIndent()

        withContext(Dispatchers.IO) {
            val batchParams = timeSlots.busyTimes.map { busyTime ->
                MapSqlParameterSource()
                    .addValue("escapeRoomId", timeSlots.escapeRoomId)
                    .addValue("startTime", busyTime.startTime)
                    .addValue("endTime", busyTime.endTime)
                    .addValue("isAvailable", true)
            }

            jdbcTemplate.batchUpdate(sql, batchParams.toTypedArray())
        }
    }
}