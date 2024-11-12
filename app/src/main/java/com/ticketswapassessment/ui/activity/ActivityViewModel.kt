package com.ticketswapassessment.ui.activity

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(activityRepository: ActivityRepository) : ViewModel() {
    val isConnectedFlow = activityRepository.isConnected
}