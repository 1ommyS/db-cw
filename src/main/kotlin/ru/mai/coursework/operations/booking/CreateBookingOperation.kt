package ru.mai.coursework.operations.booking

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.mai.coursework.controller.http.booking.dto.CreateBookingRequest
import ru.mai.coursework.entity.Booking
import ru.mai.coursework.entity.BookingStatus
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.infrastructure.repository.booking.BookingRepository
import java.time.LocalDateTime

@Service
@Log
@Transactional
class CreateBookingOperation(
    private val bookingRepository: BookingRepository,
) {
    suspend operator fun invoke(request: CreateBookingRequest) {
        val booking =
            Booking(
                userId = request.userId,
                timeSlotId = request.timeSlotId,
                bookingTime = LocalDateTime.now(),
                status = BookingStatus.PENDING,
                bookingId = null,
            )
        bookingRepository.save(booking)
    }
}
