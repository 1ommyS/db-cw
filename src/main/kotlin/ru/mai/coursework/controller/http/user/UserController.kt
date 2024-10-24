package ru.mai.coursework.controller.http.user

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.mai.coursework.controller.http.user.dto.UpdateUserDto
import ru.mai.coursework.entity.User
import ru.mai.coursework.operations.user.FindUsersOperation
import ru.mai.coursework.operations.user.UpdateUserOperation

@RestController
@RequestMapping("/api/user")
class UserController(
    private val findUsersOperation: FindUsersOperation,
    private val updateUserOperation: UpdateUserOperation
) {
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    suspend fun getUsers() = findUsersOperation.findAll()

    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    suspend fun getUserById(@PathVariable id: Int) = findUsersOperation.findById(id)

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    suspend fun updateUser(@AuthenticationPrincipal user: User, @RequestBody dto: UpdateUserDto) {
        dto.id = user.id

        updateUserOperation(dto)
    }
}