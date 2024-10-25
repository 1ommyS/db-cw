package ru.mai.coursework.operations.escapeRooms;

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.mai.coursework.entity.EscapeRoom
import ru.mai.coursework.infrastructure.repository.escapeRoom.EscapeRoomRepository

@Service
@Transactional
class CreateEscapeRoomOperation(
    private val escapeRoomRepository: EscapeRoomRepository
) {
    suspend fun invoke(entity: EscapeRoom) = withContext(Dispatchers.IO) {
        escapeRoomRepository.save(entity)
    }
}