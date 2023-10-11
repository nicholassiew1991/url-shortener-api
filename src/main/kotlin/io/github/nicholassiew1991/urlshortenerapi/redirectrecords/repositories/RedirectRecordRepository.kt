package io.github.nicholassiew1991.urlshortenerapi.redirectrecords.repositories

import io.github.nicholassiew1991.urlshortenerapi.redirectrecords.repositories.entities.RedirectRecord
import org.springframework.data.jpa.repository.JpaRepository

interface RedirectRecordRepository : JpaRepository<RedirectRecord, Long> {
}