package ru.mai.coursework.infrastructure.repository.role

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.mai.coursework.entity.Role

@Repository
class RoleRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    suspend fun getClientRole(): Role? {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM role WHERE name = 'CLIENT'",
            Role::class.java
        )
    }


}