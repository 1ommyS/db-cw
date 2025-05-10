package ru.mai.coursework.operations.timeSlots

import org.springframework.stereotype.Service
import ru.mai.coursework.entity.TimeSlot
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.infrastructure.repository.timeSlots.TimeSlotsRepository

@Service
@Log
class GetAvailableTimeSlotsOperation(
    private val timeSlotsRepository: TimeSlotsRepository,
) {
    suspend operator fun invoke(timeSlot: TimeSlot) = timeSlotsRepository.getAvailableTimeSlots(timeSlot)
}
