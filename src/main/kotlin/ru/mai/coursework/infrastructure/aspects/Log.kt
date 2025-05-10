package ru.mai.coursework.infrastructure.aspects

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Log(
    val level: LogLevel = LogLevel.INFO,
    val logArgs: Boolean = true,
    val logResult: Boolean = true,
)

enum class LogLevel {
    TRACE,
    DEBUG,
    INFO,
    WARN,
    ERROR,
}
