package ru.mai.coursework.entity

import java.math.BigDecimal
import java.sql.ResultSet
import java.time.LocalDateTime

data class EscapeRoom(
    val id: Int,
    val name: String,
    val description: String?,
    val difficultyLevel: String?,
    val maxParticipants: Int,
    val price: BigDecimal,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)

fun ResultSet.toEscapeRoom(): EscapeRoom =
    EscapeRoom(
        id = getInt("id"),
        name = getString("name"),
        description = getString("description"),
        difficultyLevel = getString("difficulty_level"),
        maxParticipants = getInt("max_participants"),
        price = getBigDecimal("price"),
        createdAt = getTimestamp("created_at").toLocalDateTime(),
        updatedAt = getTimestamp("updated_at").toLocalDateTime(),
    )
