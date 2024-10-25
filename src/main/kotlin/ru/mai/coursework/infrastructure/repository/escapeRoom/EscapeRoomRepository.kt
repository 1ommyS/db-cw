package ru.mai.coursework.infrastructure.repository.escapeRoom

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import org.springframework.transaction.support.TransactionTemplate
import ru.mai.coursework.entity.EscapeRoom

@Repository
class EscapeRoomRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val transactionTemplate: TransactionTemplate
) {

    suspend fun getAvailableEscapeRooms(): List<EscapeRoom> {
        val sql = """
                SELECT id,
                name,
                description,
                difficulty_level,
                max_participants,
                price,
                created_at,
                updated_at
                FROM AvailableEscapeRooms;
            """.trimIndent()

        return jdbcTemplate.queryForObject<List<EscapeRoom>>(sql)
    }

    suspend fun findAll(): List<EscapeRoom> {
        val sql = """
                SELECT id,
                name,
                description,
                difficulty_level,
                max_participants,
                price,
                created_at,
                updated_at
                FROM escape_rooms;
            """.trimIndent()

        return jdbcTemplate.queryForObject<List<EscapeRoom>>(sql)

    }

    suspend fun save(entity: EscapeRoom) {
        transactionTemplate.executeWithoutResult {
            jdbcTemplate.update(
                "INSERT INTO escape_rooms (name, description, difficulty_level, max_participants, price) VALUES (?, ?, ?, ?, ?)",
                entity.name,
                entity.description,
                entity.difficultyLevel,
                entity.maxParticipants,
                entity.price
            )
        }
    }
}