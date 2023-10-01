package io.github.nicholassiew1991.urlshortenerapi.links

import io.github.nicholassiew1991.urlshortenerapi.links.repositories.entities.Link

interface LinkService {

  fun create(url: String): Link

}