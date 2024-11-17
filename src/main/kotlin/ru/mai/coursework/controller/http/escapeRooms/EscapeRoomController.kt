package ru.mai.coursework.controller.http.escapeRooms

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mai.coursework.controller.http.escapeRooms.dto.CreateEscapeRoomRequest
import ru.mai.coursework.controller.http.escapeRooms.dto.EscapeRoomMapper
import ru.mai.coursework.controller.http.escapeRooms.dto.EscapeRoomResponse
import ru.mai.coursework.controller.http.escapeRooms.dto.UpdateEscapeRoomRequest
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.operations.escapeRooms.*

@RestController
@Log
@RequestMapping("/escapeRooms")
class EscapeRoomController(
    private val getAvailableEscapeRooms: GetAvailableEscapeRoomsOperation,
    private val getAllEscapeRooms: GetAllEscapeRoomsOperation,
    private val createEscapeRoom: CreateEscapeRoomOperation,
    private val updateEscapeRoom: UpdateEscapeRoomOperation,
    private val deleteEscapeRoom: DeleteEscapeRoomOperation,
    private val escapeRoomMapper: EscapeRoomMapper
) {

    @GetMapping("/available")
    @Operation(summary = "Получить список всех доступных комнат", description = "Получает список всех доступных комнат")
    @PreAuthorize("isAuthenticated()")
    suspend fun getAvailableEscapeRooms(): List<EscapeRoomResponse> {
        val availableRooms = getAvailableEscapeRooms.invoke()
        return escapeRoomMapper.toDto(availableRooms)
    }

    @GetMapping
    @Operation(summary = "Получить список всех комнат", description = "Получает список всех комнат")
    @PreAuthorize("hasRole('ADMIN')")
    suspend fun getAllEscapeRooms(): List<EscapeRoomResponse> {
        val allRooms = getAllEscapeRooms.invoke()
        return escapeRoomMapper.toDto(allRooms)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Создать новую комнату", description = "Создает новую комнату по переданным данным")
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    suspend fun deleteEscapeRoom(
        @PathVariable id: Int,
    ) {
       deleteEscapeRoom.invoke(id)
    }
}