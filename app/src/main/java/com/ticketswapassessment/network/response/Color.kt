package com.ticketswapassessment.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Color(
    @Json(name = "hex")
    val hex: String?,
    @Json(name = "percent")
    val percent: Int?,
    @Json(name = "percentage")
    val percentage: Int?
)