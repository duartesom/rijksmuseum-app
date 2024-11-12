package com.ticketswapassessment.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Acquisition(
    @Json(name = "creditLine")
    val creditLine: String?,
    @Json(name = "date")
    val date: String?,
    @Json(name = "method")
    val method: String?
)