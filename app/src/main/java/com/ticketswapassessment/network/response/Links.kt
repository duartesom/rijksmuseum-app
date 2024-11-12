package com.ticketswapassessment.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Links(
    @Json(name = "self") var self: String? = "",
    @Json(name = "web") var web: String? = ""
)