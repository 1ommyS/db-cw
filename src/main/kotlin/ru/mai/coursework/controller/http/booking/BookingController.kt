package ru.mai.coursework.controller.http.booking

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.mai.coursework.controller.http.booking.dto.CreateBookingRequest
import ru.mai.coursework.controller.http.booking.dto.GetBookingResponse
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.operations.booking.CreateBookingOperation
import ru.mai.coursework.operations.booking.GetBookingOperation

@RestController
@Log
@RequestMapping("/booking")
class BookingController(
    private val createBookingOperation: CreateBookingOperation,
    private val getBookingOperation: GetBookingOperation,
) {

    /**
     * Создает новое бронирование.
     *
     * @param request Данные для создания нового бронирования.
     * @return Созданное бронирование.
     */
    @Operation(summary = "Создать новое бронирование", description = "Создает новое бронирование по переданным данным")
    @PostMapping(
        "/bookings",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    suspend fun createBooking(
        @RequestBody
        @Parameter(description = "Данные для создания нового бронирования")
        request: CreateBookingRequest
    ) {
        return createBookingOperation(request)
    }

    /**
     * Получает бронирование по идентификатору.
     *
     * @param bookingId Идентификатор бронирования.
     * @return Бронирование с указанным идентификатором.
     */
    @Operation(
        summary = "Получить бронирование по идентификатору",
        description = "Получает бронирование по переданному идентификатору"
    )
    @GetMapping("/bookings/{bookingId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getBooking(
        @PathVariable
        @Parameter(description = "Идентификатор бронирования")
        bookingId: Int
    ): GetBookingResponse = getBookingOperation(bookingId)
}