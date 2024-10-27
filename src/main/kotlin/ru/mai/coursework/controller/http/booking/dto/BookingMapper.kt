package ru.mai.coursework.controller.http.booking.dto

import org.mapstruct.*
import ru.mai.coursework.entity.Booking

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class BookingMapper {

    abstract fun toEntity(getBookingResponse: GetBookingResponse): Booking

    abstract fun toDto(booking: Booking): GetBookingResponse

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(getBookingResponse: GetBookingResponse, @MappingTarget booking: Booking): Booking
}