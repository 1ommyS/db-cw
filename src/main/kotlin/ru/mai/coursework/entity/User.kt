package ru.mai.coursework.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCrypt
import java.time.OffsetDateTime
import java.util.Date

data class User(
    val id: Int = 0,
    val username: String = "",
    val fullName: String = "",
    val email: String = "",
    val passwordHash: String = "",
    val birthDate: Date,
    val phone: String,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val role: Role
) :UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?>? = listOf(role)

    override fun getPassword(): String? = passwordHash

    override fun getUsername(): String? = username
}