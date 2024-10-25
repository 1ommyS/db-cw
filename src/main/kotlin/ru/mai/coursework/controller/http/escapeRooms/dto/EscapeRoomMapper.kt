package ru.mai.coursework.controller.http.escapeRooms.dto

import org.mapstruct.*
import ru.mai.coursework.entity.EscapeRoom

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class EscapeRoomMapper {

    abstract fun toEntity(escapeRoomResponse: CreateEscapeRoomRequest): EscapeRoom
    abstract fun toEntity(escapeRoomResponse: UpdateEscapeRoomRequest): EscapeRoom

    abstract fun toDto(escapeRoom: EscapeRoom): EscapeRoomResponse
    abstract fun toDto(escapeRoom: List<EscapeRoom>): List<EscapeRoomResponse>

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(
        escapeRoomResponse: EscapeRoomResponse,
        @MappingTarget escapeRoom: EscapeRoom
    ): EscapeRoom
}