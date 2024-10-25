package ru.mai.coursework.controller.http.escapeRooms.dto

import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * DTO for {@link ru.mai.coursework.entity.EscapeRoom}
 */
data class UpdateEscapeRoomRequest(
    val name: String?,
    val description: String? = null,
    val difficultyLevel: String? = null,
    val maxParticipants: Int?,
    val price: BigDecimal?,
)