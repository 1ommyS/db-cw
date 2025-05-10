package ru.mai.coursework.controller.http.booking.dto

import org.mapstruct.*
import ru.mai.coursework.entity.Booking

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class BookingMapper {
    abstract suspend fun toEntity(getBookingResponse: GetBookingResponse): Booking

    abstract suspend fun toDto(booking: Booking): GetBookingResponse

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract suspend fun partialUpdate(
        getBookingResponse: GetBookingResponse,
        @MappingTarget booking: Booking,
    ): Booking
}
