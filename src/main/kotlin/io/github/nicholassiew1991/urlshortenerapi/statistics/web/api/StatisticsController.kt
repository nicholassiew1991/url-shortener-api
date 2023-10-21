package io.github.nicholassiew1991.urlshortenerapi.statistics.web.api

import io.github.nicholassiew1991.urlshortenerapi.statistics.StatisticService
import org.slf4j.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/statistic")
class StatisticsController(
  private val statisticService: StatisticService,
  private val logger: Logger
) {

  @GetMapping("/{code}")
  fun getRedirectionStatistic(@PathVariable code: String): ResponseEntity<Any> {
    val result = this.statisticService.getRedirectLinkStatistic(code)
    return ResponseEntity.ok(result)
  }

}