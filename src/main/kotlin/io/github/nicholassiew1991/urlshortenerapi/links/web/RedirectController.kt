package io.github.nicholassiew1991.urlshortenerapi.links.web

import io.github.nicholassiew1991.urlshortenerapi.links.LinkService
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.view.RedirectView

@Controller
@RequestMapping("/")
class RedirectController(
  private val linkService: LinkService
) {

  @GetMapping("/{code}")
  fun redirect(
    @PathVariable code: String,
    @RequestHeader headers: Map<String, String>,
    @RequestParam queryStrings: Map<String, String>
  ): RedirectView {
    val url = this.linkService.getUrl(code) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    this.linkService.createTaskForRedirectRecord(code, headers, queryStrings)
    return RedirectView(url)
  }

}