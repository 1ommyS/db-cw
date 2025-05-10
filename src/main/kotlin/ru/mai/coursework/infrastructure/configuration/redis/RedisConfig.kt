package ru.mai.coursework.infrastructure.configuration.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.serializer.StringRedisSerializer
import ru.mai.coursework.infrastructure.pubsub.RedisPublisher
import ru.mai.coursework.infrastructure.pubsub.RedisPublisherImpl
import ru.mai.coursework.infrastructure.pubsub.RedisSubscriber

@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
data class RedisConfig(
    var host: String = "localhost",
    var port: Int = 6379,
    var password: String? = null,
    var username: String? = null,
) {
    companion object {
        const val CHANNEL_TOPIC_NAME = "critical_events"
    }

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        if (password.isNullOrEmpty()) throw IllegalArgumentException("Redis configuration: Password can't be empty")

        val factory = LettuceConnectionFactory(host, port)
        factory.setPassword(password!!)

        return factory
    }

    @Bean
    @Primary
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, String> {
        val template = RedisTemplate<String, String>()

        template.connectionFactory = redisConnectionFactory
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = StringRedisSerializer()

        return template
    }

    @Bean
    fun criticalTopic(): ChannelTopic = ChannelTopic(CHANNEL_TOPIC_NAME)

    @Bean
    fun listenerAdapter(subscriber: RedisSubscriber): MessageListenerAdapter = MessageListenerAdapter(subscriber, "onMessage")

    @Bean
    fun redisMessageListenerContainer(
        cf: RedisConnectionFactory,
        listenerAdapter: MessageListenerAdapter,
        criticalTopic: ChannelTopic,
    ): RedisMessageListenerContainer =
        RedisMessageListenerContainer().apply {
            setConnectionFactory(cf)
            addMessageListener(listenerAdapter, criticalTopic)
        }

    @Bean
    fun redisPublisher(
        redisTemplate: RedisTemplate<String, String>,
        criticalTopic: ChannelTopic,
    ): RedisPublisher = RedisPublisherImpl(redisTemplate, criticalTopic)
}
