package ru.mai.coursework

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CourseworkApplication

suspend fun main(args: Array<String>) {
    runApplication<CourseworkApplication>(*args)
}
