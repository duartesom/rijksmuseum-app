package com.ticketswapassessment.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Dating(
    @Json(name = "period")
    val period: Int?,
    @Json(name = "presentingDate")
    val presentingDate: String?,
    @Json(name = "sortingDate")
    val sortingDate: Int?,
    @Json(name = "yearEarly ")
    val yearEarly: Int?,
    @Json(name = "yearLate")
    val yearLate: Int?
)