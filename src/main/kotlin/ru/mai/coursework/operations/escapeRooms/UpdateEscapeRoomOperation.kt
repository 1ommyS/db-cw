package ru.mai.coursework.operations.escapeRooms;

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.mai.coursework.entity.EscapeRoom

@Service
@Transactional
class UpdateEscapeRoomOperation(
) {
    fun invoke(id: Int, request: EscapeRoom) {

    }
}