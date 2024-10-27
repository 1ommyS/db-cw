package ru.mai.coursework.controller.http.payment.dto

import org.mapstruct.*
import ru.mai.coursework.entity.Payment

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class PaymentMapper {

    abstract fun toEntity(createPaymentRequest: CreatePaymentRequest): Payment
}