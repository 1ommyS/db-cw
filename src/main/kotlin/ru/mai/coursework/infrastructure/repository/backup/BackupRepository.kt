package ru.mai.coursework.infrastructure.repository.backup

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.mai.coursework.entity.Backup
import ru.mai.coursework.entity.toBackup
import java.sql.ResultSet

@Repository
class BackupRepository(private val jdbcTemplate: JdbcTemplate) {
    fun saveBackup(fileName: String?) {
        val sql = "INSERT INTO backups (file_name) VALUES (?)"
        jdbcTemplate.update(sql, fileName)
    }

    fun findAll(): List<Backup> {
        val sql = "SELECT * FROM backups"
        return jdbcTemplate.query(sql) { rs, _ ->
            rs.toBackup()
        }
    }
}