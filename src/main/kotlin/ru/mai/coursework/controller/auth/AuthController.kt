package ru.mai.coursework.controller.auth

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.mai.coursework.controller.auth.dto.AuthDto
import ru.mai.coursework.operations.auth.AuthOperation
import ru.mai.coursework.operations.signup.SignUpOperation

@RestController
@RequestMapping("auth")
open class AuthController(
    private val authOperation: AuthOperation,
    private val signUpOperation: SignUpOperation,
) {

    @GetMapping
    suspend fun auth(@RequestBody userCredentials: AuthDto): String {
        return authOperation(userCredentials.login, userCredentials.password)
    }
}