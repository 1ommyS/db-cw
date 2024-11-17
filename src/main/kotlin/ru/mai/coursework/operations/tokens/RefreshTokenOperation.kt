package ru.mai.coursework.operations.tokens

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import ru.mai.coursework.controller.http.auth.dto.JwtResult
import ru.mai.coursework.controller.http.auth.dto.RefreshTokenDto
import ru.mai.coursework.infrastructure.configuration.security.providers.JwtTokenProvider
import ru.mai.coursework.utils.saveRefreshToken

@Service
data class RefreshTokenOperation(
    private val jwtTokenProvider: JwtTokenProvider,
    private val redisTemplate: RedisTemplate<String, String>
) {
    suspend operator fun invoke(refreshTokenDto: RefreshTokenDto): JwtResult {
        val username = jwtTokenProvider.getUsernameFromRefreshToken(refreshTokenDto.refreshToken)

        val storedToken = redisTemplate.opsForValue().get("refreshToken:$username")
        if (storedToken == null || storedToken != refreshTokenDto.refreshToken) {
            throw IllegalArgumentException("Invalid or expired refresh token")
        }

        val newAccessToken = jwtTokenProvider.generateToken(username)
        val newRefreshToken = jwtTokenProvider.generateRefreshToken(username)

        redisTemplate.saveRefreshToken(username, newRefreshToken)

        return JwtResult(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

}