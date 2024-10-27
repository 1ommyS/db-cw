package ru.mai.coursework.operations.role

import org.springframework.stereotype.Service
import ru.mai.coursework.entity.Role
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessException
import ru.mai.coursework.infrastructure.exceptions.base.business.BusinessExceptionCode
import ru.mai.coursework.infrastructure.repository.role.RoleRepository

@Service
class RoleOperation(
    private val roleRepository: RoleRepository,
) {

    @Log
    suspend fun getClientRole(): Role {
        return roleRepository.getClientRole() ?: throw BusinessException(BusinessExceptionCode.ROLE_NOT_FOUND)
    }

}