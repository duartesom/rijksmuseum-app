package com.ticketswapassessment.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ColorsWithNormalization(
    @Json(name = "normalizedHex")
    val normalizedHex: String?,
    @Json(name = "originalHex")
    val originalHex: String?
)