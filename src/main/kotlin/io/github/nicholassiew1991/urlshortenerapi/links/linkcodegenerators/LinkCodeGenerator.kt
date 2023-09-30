package io.github.nicholassiew1991.urlshortenerapi.links.linkcodegenerators

interface LinkCodeGenerator {

  fun generate(length: Int): String

}