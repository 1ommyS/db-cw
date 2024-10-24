package ru.mai.coursework.operations.user

import org.springframework.stereotype.Service
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessException
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessExceptionCode
import ru.mai.coursework.infrastructure.repository.user.UserRepository

@Service
class FindUsersOperation(
    private val userRepository: UserRepository
) {
    suspend fun findAll() = userRepository.findAll()
    suspend fun findById(id: Int) = userRepository.findById(id)
        ?: throw BusinessException(BusinessExceptionCode.USER_NOT_FOUND)

}