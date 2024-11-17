package ru.mai.coursework.infrastructure.repository.escapeRoom

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import org.springframework.transaction.support.TransactionTemplate
import ru.mai.coursework.entity.EscapeRoom
import ru.mai.coursework.entity.toEscapeRoom

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

        return withContext(Dispatchers.IO) {
            jdbcTemplate.query(sql) { rs, _ -> rs.toEscapeRoom() }
        }
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

        return withContext(Dispatchers.IO) {
            jdbcTemplate.query(sql) { rs, _ -> rs.toEscapeRoom() }
        }

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

    suspend fun update(id: Int, request: EscapeRoom) {
        transactionTemplate.executeWithoutResult {
            jdbcTemplate.update(
                """
                    UPDATE escape_rooms SET 
                        name = COALESCE(?, name),
                        description = COALESCE(?, description),
                        difficulty_level = COALESCE(?, difficulty_level),
                        max_participants = COALESCE(?,max_participants),
                        price = COALESCE(?, price)
                        WHERE id = ?
                    """,
                request.name,
                request.description,
                request.difficultyLevel,
                request.maxParticipants,
                request.price,
                id
            )
        }

    }

    suspend fun delete(id: Int) {
        transactionTemplate.executeWithoutResult {
            jdbcTemplate.update(
                """
                    delete from escape_rooms where id = ?
                """.trimIndent(),
                id
            )
        }
    }
}