package ru.mai.coursework.controller.http.auth.dto

data class JwtResult(
    val accessToken: String,
    val refreshToken: String

) {
}