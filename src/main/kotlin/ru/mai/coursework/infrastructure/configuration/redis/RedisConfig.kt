package ru.mai.coursework.infrastructure.configuration.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
data class RedisConfig(
    var host: String = "localhost",
    var port: Int = 6379,
    var password: String? = null,
    var username: String? = null,
) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        if (password.isNullOrEmpty()) throw IllegalArgumentException("Redis configuration: Password can't be empty")

        val factory = LettuceConnectionFactory(host, port)
        factory.setPassword(password!!)

        return factory
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, String> {
        val template = RedisTemplate<String, String>()

        template.connectionFactory = redisConnectionFactory
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = StringRedisSerializer()

        return template
    }
}