package ru.mai.coursework.operations.escapeRooms

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.infrastructure.repository.escapeRoom.EscapeRoomRepository

@Service
@Transactional
class DeleteEscapeRoomOperation(
    private val escapeRoomRepository: EscapeRoomRepository,
) {
    @Log
    suspend fun invoke(id: Int) {
        escapeRoomRepository.delete(id)
    }
}
