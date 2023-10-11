package io.github.nicholassiew1991.urlshortenerapi.redirectrecords.repositories.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Entity
@Table(name = "redirect_record")
class RedirectRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0

  var code: String = ""

  @Column(name = "redirect_datetime")
  var redirectDateTime: OffsetDateTime = OffsetDateTime.now()

  constructor()

  constructor(code: String) {
    this.code = code
  }

  constructor(id: Long, code: String, redirectDateTime: OffsetDateTime) {
    this.id = id
    this.code = code
    this.redirectDateTime = redirectDateTime
  }

}