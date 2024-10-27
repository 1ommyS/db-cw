package ru.mai.coursework.controller.http.escapeRooms.dto

import org.mapstruct.*
import org.springframework.stereotype.Component
import ru.mai.coursework.entity.EscapeRoom

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
interface EscapeRoomMapper {

    fun toEntity(escapeRoomResponse: CreateEscapeRoomRequest): EscapeRoom
    fun toEntity(escapeRoomResponse: UpdateEscapeRoomRequest): EscapeRoom

    fun toDto(escapeRoom: EscapeRoom): EscapeRoomResponse
    fun toDto(escapeRoom: List<EscapeRoom>): List<EscapeRoomResponse>
}