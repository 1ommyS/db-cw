package ru.mai.coursework.infrastructure.repository.payment

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.mai.coursework.entity.Payment
import ru.mai.coursework.entity.toPayment
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessException
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessExceptionCode

@Repository
@Log
class PaymentRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {
    suspend fun create(payment: Payment) = withContext(Dispatchers.IO) {
        val sql = """
            INSERT INTO payments (
            booking_id,
            amount,
            payment_time,
            payment_method,
            status
            ) VALUES (
            :bookingId,
            :amount,
            :paymentTime,
            :paymentMethod,
            :status
            ) RETURNING payment_id AS id
        """.trimIndent()

        val params = mapOf(
            "bookingId" to payment.bookingId,
            "amount" to payment.amount,
            "paymentTime" to payment.paymentTime,
            "paymentMethod" to payment.paymentMethod,
            "status" to payment.status
        )

        return@withContext jdbcTemplate.query(sql, params) { rs, _ -> rs.getInt("id") }
    }

    suspend fun getPayment(paymentId: Int): Payment = withContext(Dispatchers.IO) {
        val sql = """
            SELECT payment_id, booking_id, amount, payment_time, payment_method, status
            FROM payments
            WHERE payment_id = :paymentId
        """.trimIndent()

        return@withContext jdbcTemplate.queryForObject(sql, mapOf("paymentId" to paymentId)) { rs, _ -> rs.toPayment() }
            ?: throw BusinessException(BusinessExceptionCode.PAYMENT_NOT_FOUND)
    }
}