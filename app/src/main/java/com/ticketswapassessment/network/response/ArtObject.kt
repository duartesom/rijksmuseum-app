package com.ticketswapassessment.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtObject(
    @Json(name = "links") var links: Links? = null,
    @Json(name = "id") var id: String? = null,
    @Json(name = "objectNumber") var objectNumber: String,
    @Json(name = "title") var title: String,
    @Json(name = "hasImage") var hasImage: Boolean = false,
    @Json(name = "principalOrFirstMaker") var principalOrFirstMaker: String = "",
    @Json(name = "longTitle") var longTitle: String = "",
    @Json(name = "showImage") var showImage: Boolean = false,
    @Json(name = "permitDownload") var permitDownload: Boolean = true,
    @Json(name = "webImage") var webImage: WebImage? = null,
    @Json(name = "headerImage") var headerImage: HeaderImage? = null,
    @Json(name = "productionPlaces") var productionPlaces: List<String> = listOf(),
    @Json(name = "description") var description: String? = ""
)