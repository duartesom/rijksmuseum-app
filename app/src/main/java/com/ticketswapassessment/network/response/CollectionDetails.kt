package com.ticketswapassessment.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CollectionDetails(
    @Json(name = "elapsedMilliseconds") val elapsedMilliseconds: Int? = null,
    @Json(name = "artObject") val artObjectDetail: ArtObjectDetail? = null,
    @Json(name = "artObjectPage") val artObjectPage: Any? = null,
)