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
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.operations.user.FindUsersOperation
import ru.mai.coursework.operations.user.UpdateUserOperation

@RestController
@Log
@RequestMapping("/user")
class UserController(
    private val findUsersOperation: FindUsersOperation,
    private val updateUserOperation: UpdateUserOperation
) {
    /**
     * Retrieves a list of all users.
     *
     * This endpoint is accessible only to authenticated users.
     *
     * @return a list of users.
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    suspend fun getUsers() = findUsersOperation.findAll()

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to find.
     * @return the user with the given [id].
     */
    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    suspend fun getUserById(@PathVariable id: Int) = findUsersOperation.findById(id)

    /**
     * Updates the current user.
     *
     * Updates the current user with the fields from the given [dto].
     *
     * @param user the current user.
     * @param dto the user to update with.
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    suspend fun updateUser(@AuthenticationPrincipal user: User, @RequestBody dto: UpdateUserDto) {
        dto.id = user.id

        updateUserOperation(dto)
    }
}