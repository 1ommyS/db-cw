package ru.mai.coursework.infrastructure.exceptions.base.invocation

class InvocationException(
    val code: InvocationExceptionCode,
    override val cause: Throwable? = null,
) : RuntimeException(code.value, cause) {

}