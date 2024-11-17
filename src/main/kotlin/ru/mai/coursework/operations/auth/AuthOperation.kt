package ru.mai.coursework.operations.auth

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.mai.coursework.controller.http.auth.dto.JwtResult
import ru.mai.coursework.infrastructure.configuration.security.providers.JwtTokenProvider
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessException
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessExceptionCode
import ru.mai.coursework.infrastructure.exceptions.base.validation.ValidationException
import ru.mai.coursework.infrastructure.exceptions.base.validation.ValidationExceptionCode
import ru.mai.coursework.infrastructure.repository.user.UserRepository
import ru.mai.coursework.utils.saveRefreshToken
import java.util.concurrent.TimeUnit

@Service
class AuthOperation(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val redisTemplate: RedisTemplate<String, String>
) {
    suspend operator fun invoke(username: String, password: String): JwtResult {
        val userFromDb =
            userRepository.findByUsername(username) ?: throw BusinessException(BusinessExceptionCode.USER_NOT_FOUND)

        if (!passwordEncoder.matches(password, userFromDb.password)) {
            throw ValidationException(ValidationExceptionCode.ACCESS_DENIED)
        }

        val accessToken = jwtTokenProvider.generateToken(username)
        val refreshToken = jwtTokenProvider.generateRefreshToken(username)

        redisTemplate.saveRefreshToken(username, refreshToken)

        return JwtResult(accessToken = accessToken, refreshToken = refreshToken)
    }
}