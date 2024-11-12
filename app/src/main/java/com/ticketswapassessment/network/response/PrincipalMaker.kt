package com.ticketswapassessment.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PrincipalMaker(
    @Json(name = "biography")
    val biography: Any?,
    @Json(name = "dateOfBirth")
    val dateOfBirth: String?,
    @Json(name = "dateOfBirthPrecision")
    val dateOfBirthPrecision: Any?,
    @Json(name = "dateOfDeath")
    val dateOfDeath: String?,
    @Json(name = "dateOfDeathPrecision")
    val dateOfDeathPrecision: Any?,
    @Json(name = "labelDesc")
    val labelDesc: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "nationality")
    val nationality: String?,
    @Json(name = "occupation")
    val occupation: List<String?>?,
    @Json(name = "placeOfBirth")
    val placeOfBirth: String?,
    @Json(name = "placeOfDeath")
    val placeOfDeath: String?,
    @Json(name = "productionPlaces")
    val productionPlaces: List<String?>?,
    @Json(name = "qualification")
    val qualification: Any?,
    @Json(name = "roles")
    val roles: List<String?>?,
    @Json(name = "unFixedName")
    val unFixedName: String?
)