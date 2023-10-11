package io.github.nicholassiew1991.urlshortenerapi.tasks

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.nicholassiew1991.urlshortenerapi.core.constants.RedisTopicConstants
import io.github.nicholassiew1991.urlshortenerapi.tasks.constants.TaskNames
import io.github.nicholassiew1991.urlshortenerapi.tasks.executors.TaskExecutor
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.AdditionalMatchers.not
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.eq
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.slf4j.Logger
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.getBean
import org.springframework.data.redis.connection.DefaultMessage
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import java.util.stream.Stream

@ExtendWith(MockitoExtension::class)
class TaskMessageListenerTest {

  @Mock
  private lateinit var beanFactory: BeanFactory

  private var objectMapper = jacksonObjectMapper()

  @Mock
  private lateinit var logger: Logger

  private lateinit var messageLister: MessageListener

  @BeforeEach
  fun setUp() {
    this.messageLister = TaskMessageListener(
      this.beanFactory,
      this.objectMapper,
      this.logger
    )
  }

  @Test
  fun testOnMessageSuccess() {
    //// Arrange
    val taskExecutor = mock(TaskExecutor::class.java)
    `when`(this.beanFactory.getBean(eq(TaskNames.CREATE_REDIRECT_RECORD), eq(TaskExecutor::class.java))).thenReturn(taskExecutor)

    //// Act
    val taskName = TaskNames.CREATE_REDIRECT_RECORD
    val taskData = "THIS_IS_TASK_DATA"
    val body = mapOf("name" to taskName, "data" to taskData)
    val message = DefaultMessage(RedisTopicConstants.TASK.toByteArray(), this.objectMapper.writeValueAsBytes(body))
    this.messageLister.onMessage(message, null)

    //// Assert
    verify(taskExecutor).execute(any())
  }

  @Test
  fun testOnMessageFailed() {
    //// Arrange
    `when`(this.beanFactory.getBean(not(eq("VALID_TASK_NAME")), eq(TaskExecutor::class.java))).thenThrow(NoSuchBeanDefinitionException("NoSuchBeanDefinitionException"))

    //// Act & Assert
    val taskName = "INVALID_TASK_NAME"
    val taskData = "THIS_IS_TASK_DATA"
    val body = mapOf("name" to taskName, "data" to taskData)
    val message = DefaultMessage(RedisTopicConstants.TASK.toByteArray(), this.objectMapper.writeValueAsBytes(body))
    assertDoesNotThrow { this.messageLister.onMessage(message, null) }
    verify(this.logger).warn(argThat<String> { x -> x.endsWith(taskName) })
  }

}