package io.github.nicholassiew1991.urlshortenerapi.links.repositories

import io.github.nicholassiew1991.urlshortenerapi.links.repositories.entities.Link
import org.springframework.data.jpa.repository.JpaRepository

interface LinkRepository : JpaRepository<Link, Long> {

  fun findByCode(code: String): Link?

}