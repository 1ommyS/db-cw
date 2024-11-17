package ru.mai.coursework.infrastructure.repository.role

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.mai.coursework.entity.Role
import ru.mai.coursework.entity.toRole
import ru.mai.coursework.infrastructure.exceptions.base.invocation.InvocationException
import ru.mai.coursework.infrastructure.exceptions.base.invocation.InvocationExceptionCode

@Repository
class RoleRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    suspend fun getClientRole(): Role? {
        return withContext(Dispatchers.IO) {
            try {
                jdbcTemplate.query(
                    "SELECT * FROM roles WHERE role_name = 'CLIENT'",
                ) { rs, _ -> rs.toRole() }.firstOrNull()
            } catch (exception: Exception) {
                throw InvocationException(InvocationExceptionCode.GET_ROLE_ERROR)
            }
        }
    }


}