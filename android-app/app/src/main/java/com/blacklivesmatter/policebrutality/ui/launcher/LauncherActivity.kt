package com.blacklivesmatter.policebrutality.ui.launcher

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.blacklivesmatter.policebrutality.MainActivity
import com.blacklivesmatter.policebrutality.R
import com.blacklivesmatter.policebrutality.ui.extensions.observeKotlin
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject

/**
 * Shows splash screen. Splash screen provided by window background,
 * so that the image is immediately visible.
 */
class LauncherActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: LauncherViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        or View.SYSTEM_UI_FLAG_IMMERSIVE)

        viewModel.launcherTimeoutEvent.observeKotlin(this) { event ->
            when (event) {
                is LauncherViewModel.NavigationEvent.Home -> {
                    Timber.d("Navigating to matchup screen.")
                    startActivity(Intent(this@LauncherActivity, MainActivity::class.java))
                    finish()
                }
                is LauncherViewModel.NavigationEvent.Error -> {
                    Timber.e(event.exception, "Could not startup.")
                    Toast.makeText(this, event.exception.toString(), Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
        viewModel.countDownSplash()
    }
}
