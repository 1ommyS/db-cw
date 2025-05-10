package ru.mai.coursework.entity

import java.sql.ResultSet
import java.time.LocalDateTime

data class Log(
    val logId: Int,
    val userId: Int?,
    val action: String,
    val timestamp: LocalDateTime,
    val details: String?,
)

fun ResultSet.toLog(): Log =
    Log(
        logId = getInt("log_id"),
        userId = getInt("user_id").takeIf { !wasNull() },
        action = getString("action"),
        timestamp = getTimestamp("timestamp").toLocalDateTime(),
        details = getString("details"),
    )
