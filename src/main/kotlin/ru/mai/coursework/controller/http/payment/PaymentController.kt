package ru.mai.coursework.controller.http.payment

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mai.coursework.controller.http.payment.dto.CreatePaymentRequest
import ru.mai.coursework.controller.http.payment.dto.PaymentMapper
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.operations.payment.CreatePaymentOperation
import ru.mai.coursework.operations.payment.GetPaymentOperation

@RestController
@Log
@RequestMapping("/payments")
class PaymentController(
    private val createPaymentOperation: CreatePaymentOperation,
    private val getPaymentOperation: GetPaymentOperation,
    private val paymentMapper: PaymentMapper,
) {
    /**
     * Создает новый платеж.
     *
     * @param request Данные для создания нового платежа.
     * @return Созданный платеж.
     */
    @Operation(summary = "Создать новый платеж", description = "Создает новый платеж по переданным данным")
    @PreAuthorize("hasRole('USER')")
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createPayment(
        @RequestBody
        @Parameter(description = "Данные для создания нового платежа")
        request: CreatePaymentRequest,
    ) {
        val payment = request.toEntity()
        createPaymentOperation.invoke(payment)
    }
}
