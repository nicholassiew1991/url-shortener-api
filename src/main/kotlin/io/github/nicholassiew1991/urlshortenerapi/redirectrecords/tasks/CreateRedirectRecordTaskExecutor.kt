package io.github.nicholassiew1991.urlshortenerapi.redirectrecords.tasks

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.nicholassiew1991.urlshortenerapi.redirectrecords.models.RedirectRecordTaskDataModel
import io.github.nicholassiew1991.urlshortenerapi.redirectrecords.repositories.RedirectRecordRepository
import io.github.nicholassiew1991.urlshortenerapi.redirectrecords.repositories.entities.RedirectRecord
import io.github.nicholassiew1991.urlshortenerapi.tasks.constants.TaskNames
import io.github.nicholassiew1991.urlshortenerapi.tasks.executors.TaskExecutor
import org.slf4j.Logger
import org.springframework.stereotype.Component

@Component(TaskNames.CREATE_REDIRECT_RECORD)
class CreateRedirectRecordTaskExecutor(
  private val redirectRecordRepository: RedirectRecordRepository,
  private val objectMapper: ObjectMapper,
  private val logger: Logger
) : TaskExecutor {

  override fun execute(data: String) {
    val taskData: RedirectRecordTaskDataModel = this.objectMapper.readValue(data)
    this.logger.info("Create RedirectRecord for: ${taskData.code}")

    val redirectRecord = RedirectRecord(taskData.code, taskData.requestHeaders, taskData.requestQueryStrings, taskData.redirectDateTime)

    this.redirectRecordRepository.save(redirectRecord)
  }

}