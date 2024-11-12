package com.ticketswapassessment.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailResponse1(
    @Json(name = "artObject")
    val artObject: ArtObjectDetail?,
    @Json(name = " artObjectPage")
    val artObjectPage: ArtObjectPage?,
    @Json(name = "elapsedMilliseconds")
    val elapsedMilliseconds: Int?
)