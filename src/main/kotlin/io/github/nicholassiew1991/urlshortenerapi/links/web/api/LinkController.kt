package io.github.nicholassiew1991.urlshortenerapi.links.web.api

import io.github.nicholassiew1991.urlshortenerapi.links.LinkService
import io.github.nicholassiew1991.urlshortenerapi.links.web.api.models.CreateShortUrlRequestModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/link")
class LinkController(
  private val linkService: LinkService
) {

  @PostMapping
  fun create(@RequestBody body: CreateShortUrlRequestModel): ResponseEntity<Any> {
    val link = this.linkService.create(body.url)
    return ResponseEntity.ok(link)
  }

}