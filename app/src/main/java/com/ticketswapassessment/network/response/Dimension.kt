package com.ticketswapassessment.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Dimension(
    @Json(name = "part")
    val part: Any?,
    @Json(name = "precision")
    val precision: Any?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "unit")
    val unit: String?,
    @Json(name = "value")
    val value: String?
)