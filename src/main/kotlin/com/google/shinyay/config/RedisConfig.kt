package com.google.shinyay.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
@ComponentScan("com.google.shinyay")
@EnableRedisRepositories("com.google.shinyay.repository")
@PropertySource("classpath:application.yml")
class RedisConfig {

    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory = JedisConnectionFactory()

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any>? {
        return RedisTemplate<String, Any>().apply {
            setConnectionFactory(jedisConnectionFactory())
            keySerializer = StringRedisSerializer()
            valueSerializer = GenericToStringSerializer(Any::class.java)
            hashKeySerializer = StringRedisSerializer()
            hashValueSerializer = StringRedisSerializer()
        }
    }
}