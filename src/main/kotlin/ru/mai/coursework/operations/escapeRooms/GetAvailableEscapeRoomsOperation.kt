package ru.mai.coursework.operations.escapeRooms

import org.springframework.stereotype.Service
import ru.mai.coursework.entity.EscapeRoom
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.infrastructure.repository.escapeRoom.EscapeRoomRepository

@Service
class GetAvailableEscapeRoomsOperation(
    private val escapeRoomRepository: EscapeRoomRepository,
) {
    @Log
    suspend operator fun invoke(): List<EscapeRoom> = escapeRoomRepository.getAvailableEscapeRooms()
}
