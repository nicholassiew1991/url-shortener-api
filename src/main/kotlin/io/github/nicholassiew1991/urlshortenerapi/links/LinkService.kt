package io.github.nicholassiew1991.urlshortenerapi.links

import io.github.nicholassiew1991.urlshortenerapi.links.repositories.entities.Link

interface LinkService {

  fun getUrl(code: String): String?

  fun create(url: String): Link

  fun createTaskForRedirectRecord(
    code: String,
    requestHeaders: Map<String, String>,
    requestQueryStrings: Map<String, String>
  )

}