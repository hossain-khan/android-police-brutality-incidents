package com.blacklivesmatter.policebrutality

import androidx.lifecycle.ViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * The ViewModel for [MainActivity].
 * For now there is nothing special going on here. All actions are managed in Fragments.
 */
class MainViewModel @Inject constructor() : ViewModel() {
    init {
        Timber.d("Main ViewModel loaded.")
    }
}
