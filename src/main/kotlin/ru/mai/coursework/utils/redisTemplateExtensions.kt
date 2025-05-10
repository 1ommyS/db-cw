package ru.mai.coursework.utils

import org.springframework.data.redis.core.RedisTemplate
import ru.mai.coursework.infrastructure.configuration.security.providers.JwtTokenProvider
import java.util.concurrent.TimeUnit

suspend fun RedisTemplate<String, String>.saveRefreshToken(
    username: String,
    refreshToken: String,
    ttl: Long = JwtTokenProvider.RefreshExpirationMs.toLong(),
    units: TimeUnit = TimeUnit.MILLISECONDS,
) {
    this.opsForValue().set(
        "refreshToken:$username",
        refreshToken,
        ttl,
        units,
    )
}
