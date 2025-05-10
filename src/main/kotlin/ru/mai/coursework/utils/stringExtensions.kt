package ru.mai.coursework.utils

import org.springframework.security.crypto.password.PasswordEncoder

suspend fun String.encode(encoder: PasswordEncoder) = encoder.encode(this)
