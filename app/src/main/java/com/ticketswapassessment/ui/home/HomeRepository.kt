package com.ticketswapassessment.ui.home

import com.ticketswapassessment.network.ApiResult
import com.ticketswapassessment.network.MuseumApi
import com.ticketswapassessment.network.response.ArtObject
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: MuseumApi
) {
    suspend fun getArtObjectsByInvolvedMaker(involvedMaker: String): ApiResult<List<ArtObject>> {
        return try {
            val response = api.getCollectionByInvolvedMaker(involvedMaker)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ApiResult.Success(body.artObjects)
            }
            ApiResult.Error(Exception("response was not successful - ${response.code()}"), "")
        } catch (e: Exception) {
            if(e is UnknownHostException || e is SocketTimeoutException){
                ApiResult.Error(e, "Network connectivity error.")
            } else {
                ApiResult.Error(e, "")
            }
        }
    }

    suspend fun searchTerms(query: String): ApiResult<List<String>> {
        return try {
            val response = api.searchTerms(query = query)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ApiResult.Success(body.map { it.name })
            }
            ApiResult.Error(Exception("response was not successful - ${response.code()}"), "")
        } catch (e: Exception) {
            if(e is UnknownHostException || e is SocketTimeoutException){
                ApiResult.Error(e, "Network connectivity error.")
            } else {
                ApiResult.Error(e, "")
            }
        }
    }
}