package io.github.nicholassiew1991.urlshortenerapi.links.exceptions

class LinkCodeExistException(
  private val code: String
) : RuntimeException("Code '$code' is existed in database") {
}