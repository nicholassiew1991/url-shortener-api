package io.github.nicholassiew1991.urlshortenerapi.redirectrecords.tasks

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.nicholassiew1991.urlshortenerapi.redirectrecords.repositories.RedirectRecordRepository
import io.github.nicholassiew1991.urlshortenerapi.tasks.executors.TaskExecutor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import org.slf4j.Logger

@ExtendWith(MockitoExtension::class)
class CreateRedirectRecordTaskExecutorTest {

  @Mock
  private lateinit var redirectRecordRepository: RedirectRecordRepository

  @Mock
  private lateinit var logger: Logger

  private lateinit var executor: TaskExecutor;

  @BeforeEach
  fun setup() {
    this.executor = CreateRedirectRecordTaskExecutor(
      this.redirectRecordRepository,
      jacksonObjectMapper(),
      this.logger
    )
  }

  @Test
  fun testExecute() {
    //// Arrange
    val code = "this_is_code"
    val taskData = "{\"code\":\"$code\"}"

    //// Act & Assert
    assertDoesNotThrow { this.executor.execute(taskData) }
    verify(this.redirectRecordRepository).save(argThat { x -> x.code == code })
  }

}