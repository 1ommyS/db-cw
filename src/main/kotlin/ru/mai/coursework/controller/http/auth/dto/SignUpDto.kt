package ru.mai.coursework.controller.http.auth.dto

import java.util.Date

data class SignUpDto(
    val username: String,
    val password: String,
    val fullName: String,
    val email: String,
    val birthDate: Date,
    val phone: String,
) {
}