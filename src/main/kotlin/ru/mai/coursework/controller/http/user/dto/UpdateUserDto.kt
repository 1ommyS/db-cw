package ru.mai.coursework.controller.http.user.dto

import org.springframework.security.crypto.password.PasswordEncoder
import ru.mai.coursework.utils.encode
import java.util.*

data class UpdateUserDto(
    var id: Int,
    val username: String?,
    val fullName: String?,
    val email: String?,
    var password: String?,
    val birthDate: Date,
    val phone: String?,
    val roleId: Int?
) {

    fun encodePasswordIfNeccessary(passwordEncoder: PasswordEncoder) {
        password = password?.encode(encoder = passwordEncoder)
    }
}