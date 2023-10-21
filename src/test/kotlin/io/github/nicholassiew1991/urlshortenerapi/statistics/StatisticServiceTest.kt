package io.github.nicholassiew1991.urlshortenerapi.statistics

import io.github.nicholassiew1991.urlshortenerapi.statistics.api.StatisticDataProvider
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger

@ExtendWith(MockitoExtension::class)
class StatisticServiceTest {

  @Mock
  private lateinit var statisticDataProvider: StatisticDataProvider

  @Mock
  private lateinit var logger: Logger

  private lateinit var statisticService: StatisticService

  @BeforeEach
  fun setup() {
    this.statisticService = StatisticServiceImpl(
      this.statisticDataProvider,
      this.logger
    )
  }

  @Test
  fun getRedirectLinkStatistic() {
    //// Arrange
    `when`(this.statisticDataProvider.count(anyString())).thenReturn(10)

    //// Act
    val code = "abcde"
    val statistics = this.statisticService.getRedirectLinkStatistic(code)

    //// Assert
    assertEquals(10, statistics.redirectionCount)
  }
}