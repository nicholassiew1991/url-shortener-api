package io.github.nicholassiew1991.urlshortenerapi.tasks.publishers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.nicholassiew1991.urlshortenerapi.core.constants.RedisTopicConstants
import io.github.nicholassiew1991.urlshortenerapi.tasks.constants.TaskNames
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatcher
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argThat
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.slf4j.Logger
import org.springframework.data.redis.core.RedisTemplate

@ExtendWith(MockitoExtension::class)
class RedisTaskPublisherTest {

  @Mock
  private lateinit var redisTemplate: RedisTemplate<String, Any>

  @Mock
  private lateinit var logger: Logger

  private lateinit var taskPublisher: TaskPublisher

  @BeforeEach
  fun setup() {
    this.taskPublisher = RedisTaskPublisher(
      this.redisTemplate,
      jacksonObjectMapper(),
      this.logger
    )
  }

  @Test
  fun testPublish() {
    //// Arrange
    val taskName = TaskNames.CREATE_REDIRECT_RECORD
    val data = "ANY_DATA"

    //// Act & Assert
    assertDoesNotThrow { this.taskPublisher.publish(taskName, data) }
    //verify(this.redisTemplate).convertAndSend(eq(RedisTopicConstants.TASK), argThat(ArgumentMatcher<Map<String, String>> { x -> x["name"] == taskName }))
    verify(this.redisTemplate).convertAndSend(eq(RedisTopicConstants.TASK), argThat<Map<String, String>> { x -> x["name"] == taskName && x["data"] == "\"$data\"" })
  }
}