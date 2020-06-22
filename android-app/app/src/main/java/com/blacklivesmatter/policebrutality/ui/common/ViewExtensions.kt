package com.blacklivesmatter.policebrutality.ui.common

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.blacklivesmatter.policebrutality.BuildConfig
import timber.log.Timber

/**
 * Sets visibility to VISIBLE or GONE.
 */
@BindingAdapter("visibleOrGone")
fun View.setVisibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

/**
 * Avoid:
 *  java.lang.IllegalArgumentException: navigation destination XXX is unknown to this NavController.
 *
 * @param sourceFragmentId From navigation graph:
 *   <fragment android:id="@+id/sourceFragmentId"
 *      <action android:id="@+id/destinationActionId" />
 *   </fragment>
 */
@SuppressLint("BinaryOperationInTimber")
fun Fragment.navigateTo(@IdRes sourceFragmentId: Int, destinationAction: NavDirections) {
    val controller = NavHostFragment.findNavController(this)
    val currentDestination = controller.currentDestination?.id
    if (currentDestination == sourceFragmentId) {
        if (BuildConfig.DEBUG) {
            Timber.i(
                "Navigating from %s via action %s",
                this.resources.getResourceName(sourceFragmentId),
                this.resources.getResourceName(destinationAction.actionId)
            )
        }
        controller.navigate(destinationAction)
    } else {
        Timber.w(
            "Ignoring navigation because current destination has changed. " +
                    "Expected %s, actual %s/%s, desired destination %s",
            this.resources.getResourceName(sourceFragmentId),
            if (currentDestination != null) {
                this.resources.getResourceName(currentDestination)
            } else {
                null
            },
            this,
            this.resources.getResourceName(destinationAction.actionId)
        )
    }
}
