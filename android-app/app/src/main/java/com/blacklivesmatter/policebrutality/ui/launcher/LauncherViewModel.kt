package com.blacklivesmatter.policebrutality.ui.launcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blacklivesmatter.policebrutality.ui.extensions.LiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * View model for [LauncherActivity].
 */
class LauncherViewModel @Inject constructor() : ViewModel() {
    companion object {
        const val SPLASH_SCREEN_DELAY_FIRST_TIME_MS = 1600L
        const val SPLASH_SCREEN_DELAY_MS = 1000L
    }

    sealed class NavigationEvent {
        object Home : NavigationEvent()
        data class Error(val exception: Exception) : NavigationEvent()
    }

    private val _launcherTimeoutEvent = LiveEvent<NavigationEvent>()
    val launcherTimeoutEvent: LiveData<NavigationEvent> = _launcherTimeoutEvent

    fun countDownSplash() {
        viewModelScope.launch {
            Timber.d("Waiting for splash delay...")
            delay(SPLASH_SCREEN_DELAY_MS)
            _launcherTimeoutEvent.value = NavigationEvent.Home
        }
    }
}
