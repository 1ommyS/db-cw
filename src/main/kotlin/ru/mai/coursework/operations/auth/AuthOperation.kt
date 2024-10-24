package ru.mai.coursework.operations.auth

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.mai.coursework.infrastructure.configuration.security.providers.JwtTokenProvider
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessException
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessExceptionCode
import ru.mai.coursework.infrastructure.exceptions.base.validation.ValidationException
import ru.mai.coursework.infrastructure.exceptions.base.validation.ValidationExceptionCode
import ru.mai.coursework.infrastructure.repository.user.UserRepository

@Service
class AuthOperation(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    suspend operator fun invoke(username: String, password: String): String {
        val userFromDb =
            userRepository.findByUsername(username) ?: throw BusinessException(BusinessExceptionCode.USER_NOT_FOUND)

        !passwordEncoder.matches(
            password,
            userFromDb.password
        ) && throw ValidationException(ValidationExceptionCode.ACCESS_DENIED)

        return jwtTokenProvider.generateToken(username)
    }
}