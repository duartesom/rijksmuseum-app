package com.ticketswapassessment.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchTermResponse(
    @Json(name = "id") var id: String,
    @Json(name = "name") var name: String
)