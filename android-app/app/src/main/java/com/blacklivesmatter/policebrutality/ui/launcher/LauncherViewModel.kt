package com.blacklivesmatter.policebrutality.ui.launcher

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blacklivesmatter.policebrutality.ui.extensions.LiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * View model for [LauncherActivity].
 */
class LauncherViewModel @ViewModelInject constructor(
    private val preferences: SharedPreferences
) : ViewModel() {
    companion object {
        private const val SPLASH_SCREEN_DELAY_FIRST_TIME_MS = 3500L
        private const val SPLASH_SCREEN_DELAY_MS = 1200L
        private const val PREF_KEY_FIRST_LAUNCH = "preference_key_first_time_app_launch"
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

            val isFirstTime = preferences.getBoolean(PREF_KEY_FIRST_LAUNCH, true)
            if (isFirstTime) {
                Timber.d("User has launched app for first time, show splash for extended time.")
                preferences.edit().putBoolean(PREF_KEY_FIRST_LAUNCH, false).apply()
            } else {
                Timber.d("Not first time app launch, showing quicker splash screen.")
            }

            delay(if (isFirstTime) SPLASH_SCREEN_DELAY_FIRST_TIME_MS else SPLASH_SCREEN_DELAY_MS)
            _launcherTimeoutEvent.value = NavigationEvent.Home
        }
    }
}
