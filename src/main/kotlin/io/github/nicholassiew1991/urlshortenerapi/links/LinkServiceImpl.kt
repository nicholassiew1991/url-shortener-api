package io.github.nicholassiew1991.urlshortenerapi.links

import io.github.nicholassiew1991.urlshortenerapi.links.linkcodegenerators.LinkCodeGenerator
import io.github.nicholassiew1991.urlshortenerapi.links.repositories.LinkRepository
import io.github.nicholassiew1991.urlshortenerapi.links.repositories.entities.Link
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class LinkServiceImpl(
  private val linkCodeGenerator: LinkCodeGenerator,
  private val linkRepository: LinkRepository,
  private val logger: Logger
) : LinkService {

  override fun create(url: String): Link {
    val code = this.linkCodeGenerator.generate(5)
    val link = Link(0, code, url, OffsetDateTime.now())
    return this.linkRepository.save(link)
  }

}