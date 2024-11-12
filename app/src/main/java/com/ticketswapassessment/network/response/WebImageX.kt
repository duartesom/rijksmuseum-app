package com.ticketswapassessment.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WebImageX(
    @Json(name = "guid")
    val guid: String?,
    @Json(name = "height")
    val height: Int?,
    @Json(name = "offsetPercentageX")
    val offsetPercentageX: Int?,
    @Json(name = "offsetPercentageY")
    val offsetPercentageY: Int?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "width")
    val width: Int?
)