package io.github.nicholassiew1991.urlshortenerapi.tasks.publishers

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.nicholassiew1991.urlshortenerapi.core.constants.MessageQueueNameConstants
import org.slf4j.Logger
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.stereotype.Component

@Component
class AmqpTaskPublisher(
  private val amqpTemplate: AmqpTemplate,
  private val objectMapper: ObjectMapper,
  private val logger: Logger
) : TaskPublisher {

  override fun publish(taskName: String, data: Any) {
    this.logger.info("Publishing a task - Name: $taskName")
    val messageObj = mapOf("name" to taskName, "data" to objectMapper.writeValueAsString(data))
    this.amqpTemplate.convertAndSend(MessageQueueNameConstants.TASK, messageObj)
  }

}
