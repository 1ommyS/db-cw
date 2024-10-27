package ru.mai.coursework.infrastructure.repository.booking

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.mai.coursework.entity.Booking
import ru.mai.coursework.entity.toBooking
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessException
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessExceptionCode

@Repository
@Log
class BookingRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {
    suspend fun save(booking: Booking): Int = withContext(Dispatchers.IO) {
        val sql = """
            INSERT INTO Bookings (user_id, time_slot_id, booking_time, status)
            VALUES (:userId, :timeSlotId, :bookingTime, :status) RETURNING booking_id
        """
        val params =
            MapSqlParameterSource().addValue("userId", booking.userId).addValue("timeSlotId", booking.timeSlotId)
                .addValue("bookingTime", booking.bookingTime).addValue("status", booking.status)

        return@withContext jdbcTemplate.queryForObject(sql, params, Int::class.java) ?: throw BusinessException(
            BusinessExceptionCode.BOOKING_ERROR
        )

    }

    suspend fun findById(bookingId: Int): Booking? = withContext(
        Dispatchers.IO
    ) {
        val sql = """
            SELECT booking_id, user_id, time_slot_id, booking_time, status
            FROM bookings
            WHERE booking_id = :bookingId
        """.trimIndent()

        return@withContext jdbcTemplate.queryForObject(
            sql,
            mapOf(
                "bookingId" to bookingId
            )
        ) { rs, _ ->
            rs.toBooking()
        }
    }
}