package ru.mai.coursework.operations.payment

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.mai.coursework.entity.Payment
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.infrastructure.repository.payment.PaymentRepository

@Service
@Log
@Transactional
class GetPaymentOperation(
    private val paymentRepository: PaymentRepository,
) {
    suspend fun invoke(paymentId: Int): Payment = paymentRepository.getPayment(paymentId)
}
