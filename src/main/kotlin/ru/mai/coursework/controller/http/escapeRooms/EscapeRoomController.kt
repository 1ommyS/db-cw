package ru.mai.coursework.controller.http.escapeRooms

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mai.coursework.controller.http.escapeRooms.dto.CreateEscapeRoomRequest
import ru.mai.coursework.controller.http.escapeRooms.dto.EscapeRoomMapper
import ru.mai.coursework.controller.http.escapeRooms.dto.EscapeRoomResponse
import ru.mai.coursework.controller.http.escapeRooms.dto.UpdateEscapeRoomRequest
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.operations.escapeRooms.CreateEscapeRoomOperation
import ru.mai.coursework.operations.escapeRooms.GetAllEscapeRoomsOperation
import ru.mai.coursework.operations.escapeRooms.GetAvailableEscapeRoomsOperation
import ru.mai.coursework.operations.escapeRooms.UpdateEscapeRoomOperation

@RestController
@Log
@RequestMapping("/escapeRooms")
class EscapeRoomController(
    private val getAvailableEscapeRooms: GetAvailableEscapeRoomsOperation,
    private val getAllEscapeRooms: GetAllEscapeRoomsOperation,
    private val createEscapeRoom: CreateEscapeRoomOperation,
    private val updateEscapeRoom: UpdateEscapeRoomOperation,
    private val escapeRoomMapper: EscapeRoomMapper
) {

    @GetMapping("/available")
    @PreAuthorize("isAuthenticated()")
    suspend fun getAvailableEscapeRooms(): List<EscapeRoomResponse> {
        val availableRooms = getAvailableEscapeRooms.invoke()
        return escapeRoomMapper.toDto(availableRooms)
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    suspend fun getAllEscapeRooms(): List<EscapeRoomResponse> {
        val allRooms = getAllEscapeRooms.invoke()
        return escapeRoomMapper.toDto(allRooms)
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    suspend fun createEscapeRoom(
        @RequestBody request: CreateEscapeRoomRequest
    ) {
        createEscapeRoom.invoke(
            escapeRoomMapper.toEntity(request)
        )
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    suspend fun updateEscapeRoom(
        @PathVariable id: Int,
        @RequestBody request: UpdateEscapeRoomRequest
    ) {
        val mappedRequest = escapeRoomMapper.toEntity(request)
        updateEscapeRoom.invoke(id, mappedRequest)
    }
}