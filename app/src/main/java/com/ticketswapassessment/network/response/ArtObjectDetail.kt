package com.ticketswapassessment.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtObjectDetail(
    @Json(name = "acquisition")
    val acquisition: Acquisition?,
    @Json(name = "artistRole")
    val artistRole: Any?,
    @Json(name = "associations")
    val associations: List<Any?>?,
    @Json(name = "catRefRPK")
    val catRefRPK: List<Any?>?,
    @Json(name = "classification")
    val classification: Classification?,
    @Json(name = "colors")
    val colors: List<Color>?,
    @Json(name = "colorsWithNormalization")
    val colorsWithNormalization: List<ColorsWithNormalization>?,
    @Json(name = "copyrightHolder")
    val copyrightHolder: Any?,
    @Json(name = "dating")
    val dating: Dating?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "dimensions")
    val dimensions: List<Dimension>?,
    @Json(name = "documentation")
    val documentation: List<String>?,
    @Json(name = "exhibitions")
    val exhibitions: List<Any?>?,
    @Json(name = "hasImage")
    val hasImage: Boolean = false,
    @Json(name = "historicalPersons")
    val historicalPersons: List<String>?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "inscriptions")
    val inscriptions: List<Any?>?,
    @Json(name = "label")
    val label: Label?,
    @Json(name = "labelText")
    val labelText: Any?,
    @Json(name = "language")
    val language: String?,
    @Json(name = "left")
    val left: Left?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "longTitle")
    val longTitle: String?,
    @Json(name = "makers")
    val makers: List<Any?>?,
    @Json(name = "materials")
    val materials: List<String>?,
    @Json(name = "materialsThesaurus")
    val materialsThesaurus: List<Any?>?,
    @Json(name = "normalized32Colors")
    val normalized32Colors: List<Normalized32Color>?,
    @Json(name = "normalizedColors")
    val normalizedColors: List<Normalized32Color>?,
    @Json(name = "objectCollection")
    val objectCollection: List<String>?,
    @Json(name = "objectNumber")
    val objectNumber: String?,
    @Json(name = "objectTypes")
    val objectTypes: List<String>?,
    @Json(name = "physicalMedium")
    val physicalMedium: String?,
    @Json(name = "physicalProperties")
    val physicalProperties: List<Any?>?,
    @Json(name = "plaqueDescriptionDutch")
    val plaqueDescriptionDutch: String?,
    @Json(name = "plaqueDescriptionEnglish")
    val plaqueDescriptionEnglish: String?,
    @Json(name = "principalMaker")
    val principalMaker: String?,
    @Json(name = "principalMakers")
    val principalMakers: List<PrincipalMaker>?,
    @Json(name = "principalOrFirstMaker")
    val principalOrFirstMaker: String?,
    @Json(name = "priref")
    val priref: String?,
    @Json(name = "productionPlaces")
    val productionPlaces: List<String>?,
    @Json(name = "productionPlacesThesaurus")
    val productionPlacesThesaurus: List<Any?>?,
    @Json(name = "scLabelLine")
    val scLabelLine: String?,
    @Json(name = "showImage")
    val showImage: Boolean?,
    @Json(name = "subTitle")
    val subTitle: String?,
    @Json(name = "techniques")
    val techniques: List<Any?>?,
    @Json(name = "techniquesThesaurus")
    val techniquesThesaurus: List<Any?>?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "titles")
    val titles: List<String>?,
    @Json(name = "webImage")
    val webImage: WebImageX?
)