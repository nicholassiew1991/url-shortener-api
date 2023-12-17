package io.github.nicholassiew1991.urlshortenerapi.redirectrecords.repositories.entities

import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Type
import java.time.OffsetDateTime

@Entity
@Table(name = "redirect_record")
class RedirectRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0

  var code: String = ""

  @Type(JsonType::class)
  var requestHeaders: Map<String, String> = mapOf()

  @Type(JsonType::class)
  var requestQueryStrings: Map<String, String> = mapOf()

  @Column(name = "redirect_datetime")
  var redirectDateTime: OffsetDateTime = OffsetDateTime.now()

  constructor()

  constructor(code: String, requestHeaders: Map<String, String>, requestQueryStrings: Map<String, String>, redirectDateTime: OffsetDateTime) {
    this.code = code
    this.requestHeaders = requestHeaders
    this.requestQueryStrings = requestQueryStrings
    this.redirectDateTime = redirectDateTime
  }

}