package ru.mai.coursework.infrastructure.repository.user

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.mai.coursework.entity.User

@Repository
class UserRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    suspend fun findByUsername(username: String): User? {
        return jdbcTemplate.queryForObject("select * from users where username = ?", User::class.java, username)
    }
}