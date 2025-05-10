package ru.mai.coursework.operations.timeSlots

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.mai.coursework.controller.http.timeSlots.dto.CreateTimeSlotsRequest
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.infrastructure.repository.timeSlots.TimeSlotsRepository

@Service
@Log
@Transactional
class CreateTimeSlotsOperation(
    private val timeSlotsRepository: TimeSlotsRepository,
) {
    suspend operator fun invoke(timeSlots: CreateTimeSlotsRequest) = timeSlotsRepository.createTimeSlots(timeSlots.data)
}
