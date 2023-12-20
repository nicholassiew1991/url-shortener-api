package io.github.nicholassiew1991.urlshortenerapi.core.configurations

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.nicholassiew1991.urlshortenerapi.core.constants.MessageQueueNameConstants
import org.springframework.amqp.core.Queue
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMQConfigurations {

  @Bean
  fun taskMessageQueue(): Queue {
    return Queue(MessageQueueNameConstants.TASK)
  }

  @Bean
  fun messageConverter(objectMapper: ObjectMapper?): MessageConverter {
    return Jackson2JsonMessageConverter(objectMapper!!)
  }

}