package com.ticketswapassessment.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtObjectPage(
    @Json(name = "adlibOverrides")
    val adlibOverrides: AdlibOverrides?,
    @Json(name = "audioFile1")
    val audioFile1: Any?,
    @Json(name = "audioFileLabel1")
    val audioFileLabel1: Any?,
    @Json(name = "audioFileLabel2")
    val audioFileLabel2: Any?,
    @Json(name = "createdOn")
    val createdOn: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "lang")
    val lang: String?,
    @Json(name = "objectNumber")
    val objectNumber: String?,
    @Json(name = "plaqueDescription")
    val plaqueDescription: String?,
    @Json(name = "similarPages")
    val similarPages: List<Any?>?,
    @Json(name = "tags")
    val tags: List<Any?>?,
    @Json(name = "updatedOn")
    val updatedOn: String?
)