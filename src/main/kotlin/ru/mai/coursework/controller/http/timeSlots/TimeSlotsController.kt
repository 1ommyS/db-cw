package ru.mai.coursework.controller.http.timeSlots

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.mai.coursework.controller.http.timeSlots.dto.CreateTimeSlotsRequest
import ru.mai.coursework.controller.http.timeSlots.dto.GetAvailableTimeSlotsRequest
import ru.mai.coursework.controller.http.timeSlots.dto.GetAvailableTimeSlotsResponse
import ru.mai.coursework.controller.http.timeSlots.dto.TimeSlotMapper
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.operations.timeSlots.CreateTimeSlotsOperation
import ru.mai.coursework.operations.timeSlots.GetAvailableTimeSlotsOperation

@RequestMapping("/timeSlots")
@Log
@RestController
class TimeSlotsController(
    private val getAvailableTimeSlotsOperation: GetAvailableTimeSlotsOperation,
    private val createTimeSlotsOperation: CreateTimeSlotsOperation,
    private val timeSlotMapper: TimeSlotMapper
) {

    @Operation(summary = "Получить все доступны для брони слоты", description = "Получить все доступны для брони слоты")
    @PostMapping(
        "availableTimeSlots",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    suspend fun getAvailableTimeSlots(@RequestBody request: GetAvailableTimeSlotsRequest): GetAvailableTimeSlotsResponse {
        val entity = timeSlotMapper.toEntity(request)

        val availableTimeSlots = getAvailableTimeSlotsOperation(entity)

        return GetAvailableTimeSlotsResponse(
            availableTimeSlots = timeSlotMapper.toDto(availableTimeSlots)
        )
    }

    @Operation(summary = "Создать слоты", description = "Создать слоты")
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createTimeSlots(@RequestBody request: CreateTimeSlotsRequest) {
        createTimeSlotsOperation(request)
    }

}