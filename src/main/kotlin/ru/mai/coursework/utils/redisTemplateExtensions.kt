package ru.mai.coursework.utils

import org.springframework.data.redis.core.RedisTemplate
import ru.mai.coursework.infrastructure.configuration.security.providers.JwtTokenProvider
import java.util.concurrent.TimeUnit

fun RedisTemplate<String, String>.saveRefreshToken(username: String, refreshToken: String) {
    this.opsForValue().set(
        "refreshToken:$username",
        refreshToken,
        JwtTokenProvider.RefreshExpirationMs.toLong(),
        TimeUnit.MILLISECONDS
    )
}