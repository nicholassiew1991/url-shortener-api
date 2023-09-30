package io.github.nicholassiew1991.urlshortenerapi.links.linkcodegenerators

import org.springframework.stereotype.Service

@Service
class RandomLinkCodeGenerator : LinkCodeGenerator {

  private val characters: String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"

  override fun generate(length: Int): String {
    return (1..length)
      .map { characters.random() }
      .joinToString("")
  }

}