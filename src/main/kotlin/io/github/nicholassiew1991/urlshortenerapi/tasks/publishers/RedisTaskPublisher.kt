package io.github.nicholassiew1991.urlshortenerapi.tasks.publishers

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.nicholassiew1991.urlshortenerapi.core.constants.RedisTopicConstants
import org.slf4j.Logger
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisTaskPublisher(
  private val redisTemplate: RedisTemplate<String, Any>,
  private val objectMapper: ObjectMapper,
  private val logger: Logger
) : TaskPublisher {

  override fun publish(taskName: String, data: Any) {
    this.logger.info("Publishing a task - Name: $taskName")
    val messageObj = mapOf("name" to taskName, "data" to objectMapper.writeValueAsString(data))
    this.redisTemplate.convertAndSend(RedisTopicConstants.TASK, messageObj)
  }

}