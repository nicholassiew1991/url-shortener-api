package io.github.nicholassiew1991.urlshortenerapi.statistics.api

interface StatisticDataProvider {

  fun count(code: String): Long

}