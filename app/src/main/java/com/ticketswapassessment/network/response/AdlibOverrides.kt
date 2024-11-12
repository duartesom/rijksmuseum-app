package com.ticketswapassessment.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AdlibOverrides(
    @Json(name = "creator")
    val creator: Any?,
    @Json(name = "labelText")
    val labelText: Any?,
    @Json(name = "title")
    val title: Any?
)