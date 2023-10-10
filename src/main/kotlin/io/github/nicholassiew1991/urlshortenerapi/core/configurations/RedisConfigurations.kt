package io.github.nicholassiew1991.urlshortenerapi.core.configurations

import io.github.nicholassiew1991.urlshortenerapi.core.constants.RedisTopicConstants
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer

@Configuration
class RedisConfigurations {

  @Bean
  fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
    val template = RedisTemplate<String, Any>()
    template.connectionFactory = redisConnectionFactory
    template.valueSerializer = GenericJackson2JsonRedisSerializer()
    return template
  }

  @Bean
  fun redisMessageListenerContainer(
    redisConnectionFactory: RedisConnectionFactory,
    @Qualifier("taskMessageListener") taskMessageListener: MessageListener
  ): RedisMessageListenerContainer {
    val container = RedisMessageListenerContainer()
    container.setConnectionFactory(redisConnectionFactory)
    container.addMessageListener(taskMessageListener, ChannelTopic(RedisTopicConstants.TASK))
    return container
  }

}