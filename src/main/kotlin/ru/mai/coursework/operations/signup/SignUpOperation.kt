package ru.mai.coursework.operations.signup

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.mai.coursework.controller.http.auth.dto.SignUpDto
import ru.mai.coursework.entity.User
import ru.mai.coursework.infrastructure.configuration.security.providers.JwtTokenProvider
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessException
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessExceptionCode
import ru.mai.coursework.infrastructure.repository.user.UserRepository
import ru.mai.coursework.operations.role.RoleOperation

@Service
class SignUpOperation(
    private val userRepository: UserRepository,
    private val roleOperation: RoleOperation,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    suspend operator fun invoke(user: SignUpDto): String {
        userRepository.findByUsername(user.username)
            ?: throw BusinessException(BusinessExceptionCode.USER_ALREADY_EXISTS)

        val passwordHash = passwordEncoder.encode(user.password)
        val user = User(
            username = user.username,
            passwordHash = passwordHash,
            fullName = user.fullName,
            email = user.email,
            birthDate = user.birthDate,
            phone = user.phone,
            role = roleOperation.getClientRole(),
        )

        userRepository.save(user)

        return jwtTokenProvider.generateToken(username = user.username)
    }
}