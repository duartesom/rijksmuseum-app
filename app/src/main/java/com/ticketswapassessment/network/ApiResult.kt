package com.ticketswapassessment.network

sealed class ApiResult<out R> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception, val message: String) : ApiResult<Nothing>()
    data object Loading : ApiResult<Nothing>()
}
