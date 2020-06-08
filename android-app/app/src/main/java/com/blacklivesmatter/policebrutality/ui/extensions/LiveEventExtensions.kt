package com.blacklivesmatter.policebrutality.ui.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Allows shorter syntax when observing with functions in Kotlin.
 *
 * For example:
 * ```kotlin
 * viewModel.liveDataVar.observeKotlin(viewLifecycleOwner) { data -> Timber.d("Got $data") }
 * ```
 */
inline fun <T> LiveData<T>.observeKotlin(owner: LifecycleOwner, crossinline onChanged: (T) -> Unit) {
    this.observe(owner, Observer { data -> onChanged(data) })
}
