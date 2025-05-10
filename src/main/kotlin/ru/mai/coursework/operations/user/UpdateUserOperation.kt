package ru.mai.coursework.operations.user

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.mai.coursework.controller.http.user.dto.UpdateUserDto
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.infrastructure.repository.user.UserRepository

@Service
@Log
class UpdateUserOperation(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    suspend operator fun invoke(user: UpdateUserDto) =
        withContext(Dispatchers.IO) {
            user.encodePasswordIfNeccessary(passwordEncoder)
            userRepository.update(user)
        }
}
