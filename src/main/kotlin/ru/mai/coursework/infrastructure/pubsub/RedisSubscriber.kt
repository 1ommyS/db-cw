package ru.mai.coursework.infrastructure.pubsub

import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component
import ru.mai.coursework.infrastructure.aspects.Log

@Component
@Log
class RedisSubscriber : MessageListener {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun onMessage(
        message: Message,
        pattern: ByteArray?,
    ) {
        val channel = String(message.channel)
        val payload = String(message.body)
        logger.info("Received message from $channel. Payload is: $payload")
    }
}
