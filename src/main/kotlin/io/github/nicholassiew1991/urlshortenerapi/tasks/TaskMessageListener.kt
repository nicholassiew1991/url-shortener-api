package io.github.nicholassiew1991.urlshortenerapi.tasks

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.nicholassiew1991.urlshortenerapi.tasks.executors.TaskExecutor
import org.slf4j.Logger
import org.springframework.beans.BeansException
import org.springframework.beans.factory.BeanFactory
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
class TaskMessageListener(
  private val beanFactory: BeanFactory,
  private val objectMapper: ObjectMapper,
  private val logger: Logger
) : MessageListener {

  override fun onMessage(message: Message, pattern: ByteArray?) {

    val map = this.objectMapper.readValue(message.toString(), object: TypeReference<Map<String, String>>() {})
    val taskName = map["name"]!!
    val taskData = map["data"]!!

    this.logger.info("Received task message to execute - TaskName: $taskName")

    try {
      val executor = this.beanFactory.getBean(taskName, TaskExecutor::class.java)
      executor.execute(taskData)
    } catch (ex: BeansException) {
      this.logger.warn("Unable to resolve TaskExecutor with bean name: $taskName")
    }
  }

}