package com.ticketswapassessment.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ticketswapassessment.network.ApiResult
import com.ticketswapassessment.network.response.ArtObjectDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val detailsRepository: DetailsRepository) : ViewModel() {
    private val _detailsFlow = MutableStateFlow<ApiResult<ArtObjectDetail>>(ApiResult.Loading)
    val detailsFlow = _detailsFlow.asStateFlow()

    fun getCollectionDetails(objectNumber: String) = viewModelScope.launch {
        val details = detailsRepository.getCollectionDetails(objectNumber)
        _detailsFlow.update { details }
    }
}