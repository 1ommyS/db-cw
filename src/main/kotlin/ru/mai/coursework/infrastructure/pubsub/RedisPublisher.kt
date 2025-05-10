package ru.mai.coursework.infrastructure.pubsub

interface RedisPublisher {
    suspend fun publish(
        channel: String,
        message: String,
    )
}
