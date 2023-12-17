package io.github.nicholassiew1991.urlshortenerapi.links

import io.github.nicholassiew1991.urlshortenerapi.links.exceptions.LinkCodeExistException
import io.github.nicholassiew1991.urlshortenerapi.links.linkcodegenerators.LinkCodeGenerator
import io.github.nicholassiew1991.urlshortenerapi.links.models.RedirectRecordTaskDataModel
import io.github.nicholassiew1991.urlshortenerapi.links.repositories.LinkRepository
import io.github.nicholassiew1991.urlshortenerapi.links.repositories.entities.Link
import io.github.nicholassiew1991.urlshortenerapi.tasks.constants.TaskNames
import io.github.nicholassiew1991.urlshortenerapi.tasks.publishers.TaskPublisher
import io.github.resilience4j.retry.annotation.Retry
import org.slf4j.Logger
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class LinkServiceImpl(
  private val linkCodeGenerator: LinkCodeGenerator,
  private val linkRepository: LinkRepository,
  private val taskPublisher: TaskPublisher,
  private val logger: Logger
) : LinkService {

  @Cacheable(cacheManager = "redisCacheManager", cacheNames = ["links"], key = "#a0")
  override fun getUrl(code: String): String? {
    return this.linkRepository.findByCode(code)?.url
  }

  @Retry(name = "retryLinkCodeExists")
  override fun create(url: String): Link {
    val code = this.linkCodeGenerator.generate(5)

    if (this.linkRepository.existsByCode(code) == true) {
      throw LinkCodeExistException(code)
    }

    val link = Link(0, code, url, OffsetDateTime.now())
    return this.linkRepository.save(link)
  }

  override fun createTaskForRedirectRecord(
    code: String,
    requestHeaders: Map<String, String>,
    requestQueryStrings: Map<String, String>
  ) {
    val now = OffsetDateTime.now()
    val taskData = RedirectRecordTaskDataModel(code, requestHeaders, requestQueryStrings, now)
    this.taskPublisher.publish(TaskNames.CREATE_REDIRECT_RECORD, taskData)
  }

}