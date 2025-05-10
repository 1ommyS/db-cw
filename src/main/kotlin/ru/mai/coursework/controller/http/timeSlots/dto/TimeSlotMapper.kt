package ru.mai.coursework.controller.http.timeSlots.dto

import org.mapstruct.*
import ru.mai.coursework.entity.TimeSlot

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class TimeSlotMapper {
    abstract suspend fun toEntity(getAvailableTimeSlotsRequest: GetAvailableTimeSlotsRequest): TimeSlot

    abstract suspend fun toDto(timeSlot: TimeSlot): GetAvailableTimeSlotsResponse

    abstract suspend fun toDto(timeSlot: List<TimeSlot>): List<GetAvailableTimeSlotsResponse.Dto>

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract suspend fun partialUpdate(
        getAvailableTimeSlotsResponse: GetAvailableTimeSlotsResponse,
        @MappingTarget timeSlot: TimeSlot,
    ): TimeSlot
}
