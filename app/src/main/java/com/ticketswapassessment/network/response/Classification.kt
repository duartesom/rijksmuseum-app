package com.ticketswapassessment.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Classification(
    @Json(name = "events")
    val events: List<Any?>?,
    @Json(name = "iconClassDescription")
    val iconClassDescription: List<String?>?,
    @Json(name = "iconClassIdentifier")
    val iconClassIdentifier: List<String?>?,
    @Json(name = "motifs")
    val motifs: List<Any?>?,
    @Json(name = "objectNumbers")
    val objectNumbers: List<String?>?,
    @Json(name = "people")
    val people: List<String?>?,
    @Json(name = "periods")
    val periods: List<Any?>?,
    @Json(name = "places")
    val places: List<String?>?
)