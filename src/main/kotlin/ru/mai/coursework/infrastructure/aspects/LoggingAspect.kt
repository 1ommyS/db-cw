package ru.mai.coursework.infrastructure.aspects

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Pointcut("@annotation(ru.mai.coursework.infrastructure.aspects.Log) || @within(ru.mai.coursework.infrastructure.aspects.Log)")
    suspend fun logAnnotationPointcut() {
        // Поинткат для методов и классов, помеченных @Log
    }

    @Before("logAnnotationPointcut()")
    suspend fun logBefore(joinPoint: JoinPoint) {
        val methodSignature = joinPoint.signature as MethodSignature
        val className = methodSignature.declaringType.simpleName
        val methodName = methodSignature.name
        val args = joinPoint.args.joinToString(", ")

        logger.info("Вход в метод [{}.{}] с аргументами [{}]", className, methodName, args)
    }

    @AfterReturning(pointcut = "logAnnotationPointcut()", returning = "result")
    suspend fun logAfterReturning(
        joinPoint: JoinPoint,
        result: Any?,
    ) {
        val methodSignature = joinPoint.signature as MethodSignature
        val className = methodSignature.declaringType.simpleName
        val methodName = methodSignature.name

        logger.info("Метод [{}.{}] вернул [{}]", className, methodName, result)
    }

    @AfterThrowing(pointcut = "logAnnotationPointcut()", throwing = "exception")
    suspend fun logAfterThrowing(
        joinPoint: JoinPoint,
        exception: Throwable,
    ) {
        val methodSignature = joinPoint.signature as MethodSignature
        val className = methodSignature.declaringType.simpleName
        val methodName = methodSignature.name

        logger.error("Метод [{}.{}] выбросил исключение", className, methodName, exception)
    }

    @After("logAnnotationPointcut()")
    suspend fun logAfter(joinPoint: JoinPoint) {
        val methodSignature = joinPoint.signature as MethodSignature
        val className = methodSignature.declaringType.simpleName
        val methodName = methodSignature.name

        logger.info("Выход из метода [{}.{}]", className, methodName)
    }
}
