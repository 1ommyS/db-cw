package ru.mai.coursework.infrastructure.async

import org.springframework.stereotype.Service
import java.util.concurrent.Executors

@Service
class TaskPublisher {
    @Throws(Exception::class)
    suspend fun submit(
        command: String,
        timeoutMillis: Long,
    ) {
        val process =
            Runtime.getRuntime().exec(command.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

        val executor = Executors.newSingleThreadExecutor()

        val future =
            executor.submit<Int> {
                try {
                    return@submit process.waitFor()
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                    return@submit -1
                }
            }

        val startTime = System.currentTimeMillis()

        while (!future.isDone) {
            val elapsedTime = System.currentTimeMillis() - startTime
            if (elapsedTime > timeoutMillis) {
                future.cancel(true)
                println("too much")
            }
            Thread.sleep(1000)
        }
    }
}
