package com.sixD.leaderboard.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel for [SplashFragment].
 * Manages the splash screen delay and navigation trigger.
 */
class SplashViewModel : ViewModel() {

    private val _isStartNavigation = MutableLiveData<Boolean>(false)
    /**
     * Trigger for navigating to the next screen.
     */
    val isStartNavigation: LiveData<Boolean> = _isStartNavigation

    private val _isProgressVisible = MutableLiveData<Boolean>(false)
    /**
     * Controls the visibility of progress indicators during the splash screen.
     */
    val isProgressVisible: LiveData<Boolean> = _isProgressVisible

    init {
        // Simulated splash delay before triggering data load in Fragment
        viewModelScope.launch {
            delay(500)
            _isProgressVisible.value = true
        }
    }

    /**
     * Sets the navigation trigger to true.
     */
    fun startNavigation() {
        _isStartNavigation.value = true
    }
}
