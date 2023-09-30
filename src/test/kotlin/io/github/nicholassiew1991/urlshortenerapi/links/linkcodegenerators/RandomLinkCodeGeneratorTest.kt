package io.github.nicholassiew1991.urlshortenerapi.links.linkcodegenerators

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RandomLinkCodeGeneratorTest {

  private val linkCodeGenerator: LinkCodeGenerator = RandomLinkCodeGenerator()

  @Test
  fun testGenerate() {
    val length = 5
    val code = this.linkCodeGenerator.generate(length)
    assertEquals(length, code.length)
  }

}