package ru.mai.coursework.infrastructure.repository.backup

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.mai.coursework.entity.Backup
import ru.mai.coursework.entity.toBackup

@Repository
class BackupRepository(
    private val jdbcTemplate: JdbcTemplate,
) {
    suspend fun saveBackup(fileName: String?) {
        val sql = "INSERT INTO backups (file_name) VALUES (?)"
        jdbcTemplate.update(sql, fileName)
    }

    suspend fun findAll(): List<Backup> {
        val sql = "SELECT * FROM backups"
        return jdbcTemplate.query(sql) { rs, _ ->
            rs.toBackup()
        }
    }
}
