package com.ticketswapassessment.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Collection (
  @Json(name = "elapsedMilliseconds") var elapsedMilliseconds : Int,
  @Json(name = "count") var count : Int? = null,
  @Json(name = "artObjects") var artObjects: List<ArtObject> = emptyList()
)