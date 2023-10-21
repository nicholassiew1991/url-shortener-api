package io.github.nicholassiew1991.urlshortenerapi.statistics

import io.github.nicholassiew1991.urlshortenerapi.statistics.models.LinkRedirectionStatisticModel

interface StatisticService {

  fun getRedirectLinkStatistic(code: String): LinkRedirectionStatisticModel

}