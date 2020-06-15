package com.blacklivesmatter.policebrutality.ui.launcher

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.blacklivesmatter.policebrutality.MainActivity
import com.blacklivesmatter.policebrutality.R
import com.blacklivesmatter.policebrutality.ui.extensions.observeKotlin
import dagger.hilt.android.AndroidEntryPoint

/**
 * Shows splash screen. Splash screen provided by window background,
 * so that the image is immediately visible.
 */
@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {
    private val viewModel: LauncherViewModel by viewModels()

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
                    startActivity(Intent(this@LauncherActivity, MainActivity::class.java))
                    finish()
                }
                is LauncherViewModel.NavigationEvent.Error -> {
                    Toast.makeText(this, event.exception.toString(), Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
        viewModel.countDownSplash()
    }
}
