package ru.mai.coursework.controller.http.timeSlots.dto

import org.mapstruct.*
import ru.mai.coursework.entity.TimeSlot

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class TimeSlotMapper {

    abstract fun toEntity(getAvailableTimeSlotsRequest: GetAvailableTimeSlotsRequest): TimeSlot

    abstract fun toDto(timeSlot: TimeSlot): GetAvailableTimeSlotsResponse
    abstract fun toDto(timeSlot: List<TimeSlot>): List<GetAvailableTimeSlotsResponse.Dto>

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(
        getAvailableTimeSlotsResponse: GetAvailableTimeSlotsResponse,
        @MappingTarget timeSlot: TimeSlot
    ): TimeSlot
}