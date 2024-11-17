package ru.mai.coursework.entity

import java.sql.ResultSet
import java.time.LocalDate
import java.time.LocalDateTime

data class Backup(
    val fileName: String?,
    val createdAt: LocalDate = LocalDate.now(),
) {
}

fun ResultSet.toBackup(): Backup {
    return Backup(
        fileName = getString("file_name"),
        createdAt = getDate("created_at").toLocalDate(),
    )
}