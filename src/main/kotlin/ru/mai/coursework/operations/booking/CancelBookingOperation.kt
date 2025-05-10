package ru.mai.coursework.operations.booking

import org.springframework.stereotype.Service
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessException
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessExceptionCode
import ru.mai.coursework.infrastructure.repository.booking.BookingRepository

@Service
data class CancelBookingOperation(
    val repository: BookingRepository,
) {
    suspend operator fun invoke(
        bookingId: Int,
        userId: Int,
    ) {
        val isBookedByUser = repository.isBookedByUser(bookingId, userId)
        if (isBookedByUser) {
            throw BusinessException(BusinessExceptionCode.BOOKED_NOT_BY_USER)
        }

        repository.cancelBooking(bookingId)
    }
}
