package io.github.nicholassiew1991.urlshortenerapi.redirectrecords.models

data class RedirectRecordTaskDataModel(
  val code: String,
  val requestHeaders: Map<String, String>,
  val requestQueryStrings: Map<String, String>
)
