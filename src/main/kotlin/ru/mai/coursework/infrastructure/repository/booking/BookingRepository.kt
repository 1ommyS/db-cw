package ru.mai.coursework.infrastructure.repository.booking

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.mai.coursework.entity.Booking
import ru.mai.coursework.entity.toBooking
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.infrastructure.exceptions.base.invocation.InvocationException
import ru.mai.coursework.infrastructure.exceptions.base.invocation.InvocationExceptionCode

@Repository
@Log
class BookingRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) {
    suspend fun save(booking: Booking): Unit =
        withContext(Dispatchers.IO) {
            val sql = """
        CALL create_booking(:userId, :timeSlotId, :status)
    """
            val params =
                MapSqlParameterSource()
                    .addValue("userId", booking.userId)
                    .addValue("timeSlotId", booking.timeSlotId)
                    .addValue("status", booking.status)

            try {
                jdbcTemplate.update(sql, params)
            } catch (ex: Exception) {
                throw InvocationException(InvocationExceptionCode.BOOKING_ERROR, ex)
            }
        }

    suspend fun findById(bookingId: Int): Booking? =
        withContext(
            Dispatchers.IO,
        ) {
            val sql =
                """
                SELECT booking_id, user_id, time_slot_id, booking_time, status
                FROM bookings
                WHERE booking_id = :bookingId
                """.trimIndent()

            return@withContext jdbcTemplate.queryForObject(
                sql,
                mapOf(
                    "bookingId" to bookingId,
                ),
            ) { rs, _ ->
                rs.toBooking()
            }
        }

    suspend fun cancelBooking(bookingId: Int) =
        withContext(Dispatchers.IO) {
            val sql = """
          CALL cancel_booking(:bookingId)
            """

            val params = mapOf("bookingId" to bookingId)

            return@withContext jdbcTemplate.update(sql, params)
        }

    suspend fun isBookedByUser(
        bookingId: Int,
        userId: Int,
    ): Boolean {
        val sql = """
        SELECT 1
        FROM Bookings
        WHERE booking_id = :bookingId
          AND user_id = :userId
    """
        val params =
            MapSqlParameterSource()
                .addValue("bookingId", bookingId)
                .addValue("userId", userId)

        return jdbcTemplate.queryForObject(sql, params, Int::class.java) != null
    }
}
