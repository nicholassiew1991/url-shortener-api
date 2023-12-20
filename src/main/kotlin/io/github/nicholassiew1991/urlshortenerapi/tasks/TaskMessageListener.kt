package io.github.nicholassiew1991.urlshortenerapi.tasks

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.nicholassiew1991.urlshortenerapi.core.constants.MessageQueueNameConstants
import io.github.nicholassiew1991.urlshortenerapi.tasks.executors.TaskExecutor
import org.slf4j.Logger
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.BeansException
import org.springframework.beans.factory.BeanFactory
import org.springframework.stereotype.Component


@Component
@RabbitListener(queues = [MessageQueueNameConstants.TASK])
class TaskMessageListener(
  private val beanFactory: BeanFactory,
  private val logger: Logger
) {

  @RabbitHandler
  fun handleReceiveTaskMessage(task: Map<String, String>) {

    val taskName = task["name"]!!
    val taskData = task["data"]!!

    this.logger.info("AMQP Received task message to execute - TaskName: $taskName")

    try {
      val executor = this.beanFactory.getBean(taskName, TaskExecutor::class.java)
      executor.execute(taskData)
    } catch (ex: BeansException) {
      this.logger.warn("Unable to resolve TaskExecutor with bean name: $taskName")
    }
  }

}