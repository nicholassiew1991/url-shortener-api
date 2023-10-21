package io.github.nicholassiew1991.urlshortenerapi.statistics

import io.github.nicholassiew1991.urlshortenerapi.statistics.api.StatisticDataProvider
import io.github.nicholassiew1991.urlshortenerapi.statistics.models.LinkRedirectionStatisticModel
import org.slf4j.Logger
import org.springframework.stereotype.Service

@Service
class StatisticServiceImpl(
  private val statisticDataProvider: StatisticDataProvider,
  private val logger: Logger
) : StatisticService {

  override fun getRedirectLinkStatistic(code: String): LinkRedirectionStatisticModel {
    val redirectionCount = this.statisticDataProvider.count(code)
    return LinkRedirectionStatisticModel(code, redirectionCount)
  }

}