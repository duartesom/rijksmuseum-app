package com.ticketswapassessment.network

import com.ticketswapassessment.network.response.Collection
import com.ticketswapassessment.network.response.DetailResponse1
import com.ticketswapassessment.network.response.SearchTermResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MuseumApi {
    //https://www.rijksmuseum.nl/api/nl/collection?key=[api-key]&involvedMaker=Rembrandt+van+Rijn
    @GET("api/en/collection")
    suspend fun getCollectionByInvolvedMaker(
        /*@Query("limit") limit: Int,
        @Query("offset") offset: Int,*/
        @Query("involvedMaker") name: String
    ): Response<Collection>

    @GET("api/en/collection/{objectNumber}")
    suspend fun getCollectionDetails(
        @Path("objectNumber") objectNumber: String
    ): Response<DetailResponse1>

    //https://www.rijksmuseum.nl/en/search/advanced/terms?field=involvedMaker&q=re
    @GET("en/search/advanced/terms")
    suspend fun searchTerms(
        @Query("field") field: String = "involvedMaker",
        @Query("q") query: String
    ): Response<List<SearchTermResponse>>
}
