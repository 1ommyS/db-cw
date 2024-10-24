package ru.mai.coursework.controller.http.auth

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.mai.coursework.controller.http.auth.dto.AuthDto
import ru.mai.coursework.controller.http.auth.dto.SignUpDto
import ru.mai.coursework.operations.auth.AuthOperation
import ru.mai.coursework.operations.signup.SignUpOperation

@RestController
@RequestMapping("auth")
open class AuthController(
    private val authOperation: AuthOperation,
    private val signUpOperation: SignUpOperation,
) {

    @PostMapping
    suspend fun auth(@RequestBody userCredentials: AuthDto): String {
        return authOperation(userCredentials.login, userCredentials.password)
    }

    @PostMapping
    suspend fun signUp(@RequestBody userCredentials: SignUpDto): String {
        return signUpOperation(userCredentials)
    }

}