package io.github.nicholassiew1991.urlshortenerapi.core.configurations

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializer
import java.time.Duration


@Configuration
@EnableCaching
class CacheConfigurations {

  @Bean("redisCacheManager")
  fun cacheManager(redisConnectionFactory: RedisConnectionFactory): CacheManager {
    val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
      .entryTtl(Duration.ofMinutes(10))
      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()))

    return RedisCacheManager.RedisCacheManagerBuilder
      .fromConnectionFactory(redisConnectionFactory)
      .cacheDefaults(redisCacheConfiguration)
      .build()
  }

}