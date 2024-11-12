package com.ticketswapassessment.ui.detail

import com.ticketswapassessment.network.ApiResult
import com.ticketswapassessment.network.MuseumApi
import com.ticketswapassessment.network.response.ArtObjectDetail
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val api: MuseumApi) {
    suspend fun getCollectionDetails(objectNumber: String): ApiResult<ArtObjectDetail> {
        return try {
            val response = api.getCollectionDetails(objectNumber)
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.artObject != null) return ApiResult.Success(body.artObject)
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