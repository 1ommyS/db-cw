package ru.mai.coursework.operations.escapeRooms

import org.springframework.stereotype.Service
import ru.mai.coursework.entity.EscapeRoom
import ru.mai.coursework.infrastructure.repository.escapeRoom.EscapeRoomRepository

@Service
class GetAllEscapeRoomsOperation(
    private val escapeRoomRepository: EscapeRoomRepository
) {
    suspend fun invoke(): List<EscapeRoom> = escapeRoomRepository.findAll()

}