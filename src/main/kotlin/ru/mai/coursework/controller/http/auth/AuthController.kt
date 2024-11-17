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
import ru.mai.coursework.controller.http.auth.dto.RefreshTokenDto
import ru.mai.coursework.controller.http.auth.dto.SignUpDto
import ru.mai.coursework.infrastructure.aspects.Log
import ru.mai.coursework.operations.auth.AuthOperation
import ru.mai.coursework.operations.signup.SignUpOperation
import ru.mai.coursework.operations.tokens.RefreshTokenOperation

@RestController
@Log
@RequestMapping("/auth")
class AuthController(
    private val authOperation: AuthOperation,
    private val signUpOperation: SignUpOperation,
    private val refreshTokenOperation: RefreshTokenOperation
) {

    @Operation(summary = "Аутентификация пользователя", description = "Аутентификация пользователя по логину и паролю")
    @PostMapping(
        "/sign-in",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    suspend fun signIn(
        @RequestBody
        @Parameter(description = "Данные для аутентификации пользователя")
        userCredentials: AuthDto
    ): JwtResult {
        return authOperation(userCredentials.login, userCredentials.password)
    }

    @Operation(
        summary = "Регистрация нового пользователя",
        description = "Регистрация нового пользователя по логину, паролю и другим данным"
    )
    @PostMapping(
        "/sign-up",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    suspend fun signUp(
        @RequestBody
        @Parameter(description = "Данные для регистрации нового пользователя")
        userCredentials: SignUpDto
    ): JwtResult {
        val tokens = signUpOperation(userCredentials)
        return tokens
    }

    @Operation(
        summary = "Обновление пары токенов",
        description = "Обновляет access и refresh токены по переданному refresh токену"
    )
    @PostMapping(
        "/refresh-token",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    suspend fun refreshToken(
        @RequestBody
        @Parameter(description = "Текущий refresh токен")
        refreshTokenDto: RefreshTokenDto
    ): JwtResult {
        return refreshTokenOperation(refreshTokenDto)
    }
}