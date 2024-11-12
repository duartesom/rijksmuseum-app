package com.ticketswapassessment.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WebImage(
    @Json(name = "guid") var guid: String? = null,
    @Json(name = "offsetPercentageX") var offsetPercentageX: Int? = null,
    @Json(name = "offsetPercentageY") var offsetPercentageY: Int? = null,
    @Json(name = "width") var width: Int? = null,
    @Json(name = "height") var height: Int? = null,
    @Json(name = "url") var url: String = ""
)