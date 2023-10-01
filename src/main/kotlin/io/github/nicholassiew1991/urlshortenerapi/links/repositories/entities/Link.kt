package io.github.nicholassiew1991.urlshortenerapi.links.repositories.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Entity
@Table(name = "link")
class Link {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0

  var code: String = ""

  var url: String = ""

  @Column(name = "created_datetime")
  var createdDateTime: OffsetDateTime = OffsetDateTime.now()

  constructor()

  constructor(id: Long, code: String, url: String, createdDateTime: OffsetDateTime) {
    this.id = id
    this.code = code
    this.url = url
    this.createdDateTime = createdDateTime
  }

}