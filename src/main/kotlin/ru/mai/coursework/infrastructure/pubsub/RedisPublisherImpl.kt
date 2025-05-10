package ru.mai.coursework.infrastructure.pubsub

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Component

@Component
class RedisPublisherImpl(
    private val template: RedisTemplate<String, String>,
    private val criticalTopic: ChannelTopic,
) : RedisPublisher {
    override suspend fun publish(
        channel: String,
        message: String,
    ) {
        template.convertAndSend(channel ?: criticalTopic.topic, message)
    }
}
