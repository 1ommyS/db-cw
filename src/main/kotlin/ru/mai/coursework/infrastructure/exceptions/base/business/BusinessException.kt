package ru.mai.coursework.infrastructure.exceptions.base.business

open class BusinessException(
     val code: BusinessExceptionCode,
    override val cause: Throwable? = null,
) : RuntimeException(code.value, cause) {

}