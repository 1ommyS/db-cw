package ru.mai.coursework.controller.http.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.mai.coursework.controller.http.auth.dto.AuthDto
import ru.mai.coursework.controller.http.auth.dto.JwtResult
import ru.mai.coursework.controller.http.auth.dto.SignUpDto
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.operations.auth.AuthOperation
import ru.mai.coursework.operations.signup.SignUpOperation

@RestController
@Log
@RequestMapping("/auth")
class AuthController(
    private val authOperation: AuthOperation,
    private val signUpOperation: SignUpOperation,
) {

    /**
     * Аутентификация пользователя.
     *
     * @param userCredentials Данные для аутентификации пользователя.
     * @return Токен для доступа к защищенным ресурсам.
     */
    @Operation(summary = "Аутентификация пользователя", description = "Аутентификация пользователя по логину и паролю")
    @PostMapping("/sign-in", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.TEXT_PLAIN_VALUE])
    suspend fun signIn(
        @RequestBody
        @Parameter(description = "Данные для аутентификации пользователя")
        userCredentials: AuthDto
    ): JwtResult {
        return JwtResult(token = authOperation(userCredentials.login, userCredentials.password))
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param userCredentials Данные для регистрации нового пользователя.
     * @return Токен для доступа к защищенным ресурсам.
     */
    @Operation(
        summary = "Регистрация нового пользователя",
        description = "Регистрация нового пользователя по логину, паролю и другим данным"
    )
    @PostMapping("/sign-up", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.TEXT_PLAIN_VALUE])
    suspend fun signUp(
        @RequestBody
        @Parameter(description = "Данные для регистрации нового пользователя")
        userCredentials: SignUpDto
    ): JwtResult {
        return JwtResult(token = signUpOperation(userCredentials))
    }
}