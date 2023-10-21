package io.github.nicholassiew1991.urlshortenerapi.redirectrecords.externals

import io.github.nicholassiew1991.urlshortenerapi.redirectrecords.repositories.RedirectRecordRepository
import io.github.nicholassiew1991.urlshortenerapi.statistics.api.StatisticDataProvider
import org.slf4j.Logger
import org.springframework.stereotype.Service

@Service
class StatisticDataProviderImpl(
  private val redirectRecordRepository: RedirectRecordRepository,
  private val logger: Logger
) : StatisticDataProvider {

  override fun count(code: String): Long {
    return this.redirectRecordRepository.countByCode(code)
  }

}