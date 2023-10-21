package io.github.nicholassiew1991.urlshortenerapi.redirectrecords.externals

import io.github.nicholassiew1991.urlshortenerapi.redirectrecords.repositories.RedirectRecordRepository
import io.github.nicholassiew1991.urlshortenerapi.statistics.api.StatisticDataProvider
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger

@ExtendWith(MockitoExtension::class)
class StatisticDataProviderTest {

  @Mock
  private lateinit var redirectRecordRepository: RedirectRecordRepository

  @Mock
  private lateinit var logger: Logger

  private lateinit var statisticDataProvider: StatisticDataProvider

  @BeforeEach
  fun setup() {
    this.statisticDataProvider = StatisticDataProviderImpl(
      this.redirectRecordRepository,
      this.logger
    )
  }

  @Test
  fun testCount() {
    //// Arrange
    `when`(this.redirectRecordRepository.countByCode(anyString())).thenReturn(10)

    //// Act
    val code = "abcde"
    val count = this.statisticDataProvider.count(code)

    //// Assert
    assertEquals(10, count)
  }
}