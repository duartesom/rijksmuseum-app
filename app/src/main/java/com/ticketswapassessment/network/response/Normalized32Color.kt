package com.ticketswapassessment.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Normalized32Color(
    @Json(name = "hex")
    val hex: String?,
    @Json(name = "percent")
    val percent: Int?,
    @Json(name = "percentage")
    val percentage: Int?
)