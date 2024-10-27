package ru.mai.coursework.operations.escapeRooms;

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.mai.coursework.entity.EscapeRoom
import ru.mai.coursework.infrastructure.aspects.Log

@Service
@Transactional
class UpdateEscapeRoomOperation(
) {
    @Log
    fun invoke(id: Int, request: EscapeRoom) {

    }
}