package ru.mai.coursework.controller.http.escapeRooms.dto

import org.mapstruct.*
import ru.mai.coursework.entity.EscapeRoom

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
interface EscapeRoomMapper {
    suspend fun toEntity(escapeRoomResponse: CreateEscapeRoomRequest): EscapeRoom

    suspend fun toEntity(escapeRoomResponse: UpdateEscapeRoomRequest): EscapeRoom

    suspend fun toDto(escapeRoom: EscapeRoom): EscapeRoomResponse

    suspend fun toDto(escapeRoom: List<EscapeRoom>): List<EscapeRoomResponse>
}
