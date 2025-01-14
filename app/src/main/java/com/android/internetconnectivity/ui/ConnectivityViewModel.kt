package com.android.internetconnectivity.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.internetconnectivity.data.ConnectivityObserver
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ConnectivityViewModel(
    private val connectivityObserver: ConnectivityObserver
) : ViewModel(){
    val isConnected = connectivityObserver.isConnected.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L),
        false
    )
}