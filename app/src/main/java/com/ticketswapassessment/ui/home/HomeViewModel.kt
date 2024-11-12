package com.ticketswapassessment.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ticketswapassessment.network.ApiResult
import com.ticketswapassessment.network.response.ArtObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val _searchKeyword = MutableStateFlow("")

    private val _searchTerm = MutableStateFlow("")

    // Delay to prevent too many suggestion searches requests
    private val debouncePeriod = 2000L

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<ApiResult<List<ArtObject>>> = _searchKeyword
        .flatMapLatest { keyword ->
            flow {
                emit(ApiResult.Loading)
                emit(homeRepository.getArtObjectsByInvolvedMaker(keyword))
            }.distinctUntilChanged()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ApiResult.Loading
        )

    fun updateKeyword(newKeyword: String) {
        _searchKeyword.value = newKeyword
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val termResults: StateFlow<ApiResult<List<String>>> = _searchTerm
        .flatMapLatest { keyword ->
            flow {
                emit(ApiResult.Loading)
                delay(debouncePeriod)
                emit(homeRepository.searchTerms(keyword))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ApiResult.Success(emptyList())
        )

    fun updateTerm(term: String) {
        _searchTerm.value = term
    }
}