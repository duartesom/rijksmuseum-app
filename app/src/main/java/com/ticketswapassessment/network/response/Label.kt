package com.ticketswapassessment.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Label(
    @Json(name = "date")
    val date: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "makerLine")
    val makerLine: String?,
    @Json(name = "notes")
    val notes: String?,
    @Json(name = "title")
    val title: String?
)