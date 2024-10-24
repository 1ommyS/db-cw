package ru.mai.coursework.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.sql.ResultSet
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.Date

data class User(
    val id: Int = 0,
    val username: String = "",
    val fullName: String,
    val email: String = "",
    var passwordHash: String = "",
    val birthDate: Date,
    val phone: String?,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val role: Role
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?>? = listOf(role)

    override fun getPassword(): String? = passwordHash

    override fun getUsername(): String? = username
}

fun ResultSet.toUser(): User {
    return User(
        id = getInt("id"),
        username = getString("username"),
        passwordHash = getString("password_hash"),
        email = getString("email"),
        phone = getString("phone"),
        createdAt = getTimestamp("created_at").toLocalDateTime(),
        updatedAt = getTimestamp("updated_at").toLocalDateTime(),
        fullName = getString("ful_name"),
        role = Role(
            id = getInt("role_id"),
            name = getString("role_name")
        ),
        birthDate = getDate("birth_date")
    )
}