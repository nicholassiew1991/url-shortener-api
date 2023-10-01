package io.github.nicholassiew1991.urlshortenerapi.links

import io.github.nicholassiew1991.urlshortenerapi.links.linkcodegenerators.RandomLinkCodeGenerator
import io.github.nicholassiew1991.urlshortenerapi.links.repositories.LinkRepository
import io.github.nicholassiew1991.urlshortenerapi.links.repositories.entities.Link
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger
import java.util.stream.Stream

@ExtendWith(MockitoExtension::class)
class LinkServiceTest {

  private val linkCodeGenerator = RandomLinkCodeGenerator()

  @Mock
  private lateinit var linkRepository: LinkRepository

  @Mock
  private lateinit var logger: Logger

  @ParameterizedTest
  @MethodSource
  fun testCreate(url: String) {
    //// Stub
    `when`(linkRepository.save(Mockito.any(Link::class.java))).thenAnswer { x -> x.getArgument(0) }

    //// Act
    val service = this.createService()
    val link = service.create(url)

    //// Assert
    assertEquals(url, link.url)
    assertFalse(link.code.isBlank())
  }

  private fun createService(): LinkService {
    return LinkServiceImpl(
      this.linkCodeGenerator,
      this.linkRepository,
      this.logger
    )
  }

  companion object {

    @JvmStatic
    fun testCreate(): Stream<Arguments> = Stream.of(
      Arguments.of("https://www.google.com")
    )

  }
}