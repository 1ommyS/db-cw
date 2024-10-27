package ru.mai.coursework.controller.http.timeSlots

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.mai.coursework.controller.http.timeSlots.dto.CreateTimeSlotsRequest
import ru.mai.coursework.controller.http.timeSlots.dto.GetAvailableTimeSlotsRequest
import ru.mai.coursework.controller.http.timeSlots.dto.GetAvailableTimeSlotsResponse
import ru.mai.coursework.controller.http.timeSlots.dto.TimeSlotMapper
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.operations.timeSlots.GetAvailableTimeSlotsOperation

@RequestMapping("/timeSlots")
@Log
@RestController
class TimeSlotsController(
    private val getAvailableTimeSlotsOperation: GetAvailableTimeSlotsOperation,
    private val timeSlotMapper: TimeSlotMapper
) {

    @PostMapping("availableTimeSlots")
    suspend fun getAvailableTimeSlots(@RequestBody request: GetAvailableTimeSlotsRequest): GetAvailableTimeSlotsResponse {
        val entity = timeSlotMapper.toEntity(request)

        val availableTimeSlots = getAvailableTimeSlotsOperation(entity)

        return GetAvailableTimeSlotsResponse(
            availableTimeSlots = timeSlotMapper.toDto(availableTimeSlots)
        )
    }

    @PostMapping
    suspend fun createTimeSlots(@RequestBody request: CreateTimeSlotsRequest) {

    }

}