package ru.mai.coursework.infrastructure.repository.user

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import org.springframework.transaction.support.TransactionTemplate
import ru.mai.coursework.controller.http.user.dto.UpdateUserDto
import ru.mai.coursework.entity.User
import ru.mai.coursework.entity.toUser

@Repository
class UserRepository(
    private val jdbcTemplate: JdbcTemplate, private val transactionTemplate: TransactionTemplate
) {
    suspend fun findByUsername(username: String): User? = withContext(Dispatchers.IO) {
        val query =  """
            SELECT  
                users.id, 
                username,
                password_hash,
                email,
                phone,
                created_at,
                updated_at,
                fullname,
                role_id,
                r.role_name AS role_name,
                birth_date
                FROM users
                JOIN roles r
                ON users.role_id = r.id
                WHERE username = ?
                """.trim()
        jdbcTemplate.query(
           query,
            { rs, _ -> rs.toUser() },
            username
        ).firstOrNull()
    }

    suspend fun save(user: User) = withContext(Dispatchers.IO) {
        transactionTemplate.executeWithoutResult {
            jdbcTemplate.update(
                "INSERT INTO users (username, password_hash, fullname, email, phone, role_id, birth_date) VALUES (?, ?, ?, ?, ?)",
                user.username,
                user.password,
                user.fullName,
                user.email,
                user.phone,
                user.role.id,
                user.birthDate
            )
        }
    }

    suspend fun findAll() = withContext(Dispatchers.IO) {
        val sql = """
                SELECT 
                id, 
                username,
                password_hash,
                email,
                phone,
                created_at,
                updated_at,
                fullname,
                role_id,
                r.role_name AS role_name,
                birth_date
                FROM users
                JOIN roles r ON users.role_id = r.id
            """.trimIndent()

        return@withContext jdbcTemplate.queryForObject<List<User>>(
            sql
        )

    }

    suspend fun findById(id: Int): User? {
        val sql = """
                SELECT 
                id, 
                username,
                password_hash,
                email,
                phone,
                created_at,
                updated_at,
                fullname,
                role_id,
                r.role_name AS role_name,
                birth_date
                FROM users
                JOIN roles r ON users.role_id = r.id
                WHERE id = ?
            """.trimIndent()

        return withContext(Dispatchers.IO) {
            jdbcTemplate.query(
                sql, { rs, _ -> rs.toUser() }, id
            )
        }.firstOrNull()


    }

    suspend fun update(user: UpdateUserDto) = withContext(Dispatchers.IO) {
        transactionTemplate.executeWithoutResult {
            val sql = """
            UPDATE users 
            SET 
                username = COALESCE(?, username),
                password_hash = COALESCE(?, password_hash),
                email = COALESCE(?, email),
                phone = COALESCE(?, phone),
                fullname = COALESCE(?, fullname),
                role_id = COALESCE(?, role_id),
                birth_date = COALESCE(?, birth_date)
            WHERE id = ?
        """.trimIndent()

            jdbcTemplate.update(
                sql,
                user.username,
                user.password,
                user.email,
                user.phone,
                user.fullName,
                user.roleId,
                user.birthDate,
                user.id
            )
        }
    }
}