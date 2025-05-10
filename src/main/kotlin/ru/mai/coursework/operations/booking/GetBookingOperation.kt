package ru.mai.coursework.operations.booking

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.mai.coursework.controller.http.booking.dto.GetBookingResponse
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessException
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessExceptionCode
import ru.mai.coursework.infrastructure.repository.booking.BookingRepository
import ru.mai.coursework.operations.user.FindUsersOperation

@Service
@Log
@Transactional
class GetBookingOperation(
    private val bookingRepository: BookingRepository,
    private val usersOperation: FindUsersOperation,
) {
    suspend operator fun invoke(bookingId: Int): GetBookingResponse {
        val booking =
            bookingRepository.findById(bookingId)
                ?: throw BusinessException(BusinessExceptionCode.BOOKING_NOT_FOUND)
        val bookingOwner = usersOperation.findById(booking.userId)

        return GetBookingResponse(
            bookingId = booking.bookingId!!,
            user = bookingOwner.fullName,
            timeSlotId = booking.timeSlotId,
            bookingTime = booking.bookingTime,
            status = booking.status,
        )
    }
}
