package com.ticketswapassessment.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HeaderImage(
    @Json(name = "guid") var guid: String? = "",
    @Json(name = "offsetPercentageX") var offsetPercentageX: Int?,
    @Json(name = "offsetPercentageY") var offsetPercentageY: Int?,
    @Json(name = "width") var width: Int?,
    @Json(name = "height") var height: Int?,
    @Json(name = "url") var url: String? = ""
)