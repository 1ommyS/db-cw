package ru.mai.coursework.operations.escapeRooms

import org.springframework.stereotype.Service
import ru.mai.coursework.entity.EscapeRoom
import ru.mai.coursework.infrastructure.repository.escapeRoom.EscapeRoomRepository

@Service
class GetAvailableEscapeRoomsOperation(
    private val escapeRoomRepository: EscapeRoomRepository
) {
    suspend operator fun invoke(): List<EscapeRoom> = escapeRoomRepository.getAvailableEscapeRooms()
}