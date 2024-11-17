package ru.mai.coursework.operations.signup

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.mai.coursework.controller.http.auth.dto.JwtResult
import ru.mai.coursework.controller.http.auth.dto.SignUpDto
import ru.mai.coursework.entity.User
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.infrastructure.configuration.security.providers.JwtTokenProvider
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessException
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessExceptionCode
import ru.mai.coursework.infrastructure.repository.user.UserRepository
import ru.mai.coursework.operations.role.RoleOperation
import ru.mai.coursework.utils.saveRefreshToken

@Service
class SignUpOperation(
    private val userRepository: UserRepository,
    private val roleOperation: RoleOperation,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val redisTemplate: RedisTemplate<String, String>
) {
    @Log
    suspend operator fun invoke(user: SignUpDto): JwtResult {
        userRepository.findByUsername(user.username)?.let {
            throw BusinessException(BusinessExceptionCode.USER_ALREADY_EXISTS)
        }

        val passwordHash = passwordEncoder.encode(user.password)
        val newUser = User(
            username = user.username,
            passwordHash = passwordHash,
            fullName = user.fullName,
            email = user.email,
            birthDate = user.birthDate,
            phone = user.phone,
            role = roleOperation.getClientRole(),
        )

        userRepository.save(newUser)

        val accessToken = jwtTokenProvider.generateToken(username = newUser.username)
        val refreshToken = jwtTokenProvider.generateRefreshToken(username = newUser.username)

        redisTemplate.saveRefreshToken(newUser.username, refreshToken)

        return JwtResult(accessToken, refreshToken)
    }

}