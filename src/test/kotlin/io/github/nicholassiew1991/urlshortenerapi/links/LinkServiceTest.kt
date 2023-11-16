package io.github.nicholassiew1991.urlshortenerapi.links

import io.github.nicholassiew1991.urlshortenerapi.links.exceptions.LinkCodeExistException
import io.github.nicholassiew1991.urlshortenerapi.links.linkcodegenerators.RandomLinkCodeGenerator
import io.github.nicholassiew1991.urlshortenerapi.links.models.RedirectRecordTaskDataModel
import io.github.nicholassiew1991.urlshortenerapi.links.repositories.LinkRepository
import io.github.nicholassiew1991.urlshortenerapi.links.repositories.entities.Link
import io.github.nicholassiew1991.urlshortenerapi.tasks.constants.TaskNames
import io.github.nicholassiew1991.urlshortenerapi.tasks.publishers.TaskPublisher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argThat
import org.mockito.kotlin.eq
import org.slf4j.Logger
import java.time.OffsetDateTime
import java.util.stream.Stream

@ExtendWith(MockitoExtension::class)
class LinkServiceTest {

  private val linkCodeGenerator = RandomLinkCodeGenerator()

  @Mock
  private lateinit var linkRepository: LinkRepository

  @Mock
  private lateinit var taskPublisher: TaskPublisher

  @Mock
  private lateinit var logger: Logger

  @ParameterizedTest
  @MethodSource
  fun testGetUrl(stubQueryResult: Link?, expectedIsEmpty: Boolean) {
    //// Stub
    `when`(linkRepository.findByCode(anyString())).thenReturn(stubQueryResult)

    //// Act
    val service = this.createService()
    val link = service.getUrl(linkCodeGenerator.generate(5))

    //// Assert
    assertEquals(expectedIsEmpty, link.isNullOrBlank())
  }

  @ParameterizedTest
  @MethodSource
  fun testCreateSuccess(url: String) {
    //// Stub
    `when`(linkRepository.save(Mockito.any(Link::class.java))).thenAnswer { x -> x.getArgument(0) }
    `when`(linkRepository.existsByCode(anyString())).thenReturn(false)

    //// Act
    val service = this.createService()
    val link = service.create(url)

    //// Assert
    assertEquals(url, link.url)
    assertFalse(link.code.isBlank())
  }

  @Test
  fun testCreateFailedWithLinkCodeExistException() {
    //// Stub
    `when`(linkRepository.existsByCode(anyString())).thenReturn(true)

    //// Act & Assert
    val service = this.createService()
    assertThrows<LinkCodeExistException> { service.create("https://www.google.com")  }
  }

  @Test
  fun testCreateTaskForRedirectRecord() {
    //// Arrange
    val code = "url_code"
    val requestHeaders = mapOf<String, String>()
    val requestQueryStrings = mapOf<String, String>()

    //// Act & Assert
    val service = this.createService()
    assertDoesNotThrow { service.createTaskForRedirectRecord(code, requestHeaders, requestQueryStrings) }
    verify(this.taskPublisher).publish(eq(TaskNames.CREATE_REDIRECT_RECORD), argThat { x -> x is RedirectRecordTaskDataModel && x.code == code })
  }

  private fun createService(): LinkService {
    return LinkServiceImpl(
      this.linkCodeGenerator,
      this.linkRepository,
      this.taskPublisher,
      this.logger
    )
  }

  companion object {

    @JvmStatic
    fun testGetUrl(): Stream<Arguments> = Stream.of(
      Arguments.of(Link(0, "code", "url", OffsetDateTime.now()), false),
      Arguments.of(null, true)
    )

    @JvmStatic
    fun testCreateSuccess(): Stream<Arguments> = Stream.of(
      Arguments.of("https://www.google.com")
    )

  }
}