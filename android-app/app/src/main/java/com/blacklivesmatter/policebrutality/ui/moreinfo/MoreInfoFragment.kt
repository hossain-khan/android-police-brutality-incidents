package com.blacklivesmatter.policebrutality.ui.moreinfo

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.blacklivesmatter.policebrutality.R
import com.blacklivesmatter.policebrutality.analytics.Analytics
import com.blacklivesmatter.policebrutality.databinding.FragmentMoreInfoBinding
import com.blacklivesmatter.policebrutality.ui.extensions.observeKotlin
import com.blacklivesmatter.policebrutality.ui.util.IntentBuilder
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class MoreInfoFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var analytics: Analytics

    private val viewModel by viewModels<MoreInfoViewModel> { viewModelFactory }
    private lateinit var viewDataBinding: FragmentMoreInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentMoreInfoBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@MoreInfoFragment
            vm = viewModel
        }

        // This required to participate in providing toolbar menu on the host activity
        (requireActivity() as AppCompatActivity).setSupportActionBar(viewDataBinding.toolbar)

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupHashTagHandler()
        handleExternalUrl()
    }

    override fun onStart() {
        super.onStart()
        activity?.let { analytics.logPageView(it, Analytics.SCREEN_MORE_INFO) }
    }

    private fun handleExternalUrl() {
        viewModel.openExternalUrl.observeKotlin(viewLifecycleOwner) { url ->
            analytics.logSelectItem(Analytics.CONTENT_TYPE_PB2020_LINK, url, url)
            val intent = IntentBuilder.build(requireContext(), url)
            if (intent != null) {
                startActivity(intent)
            } else {
                Snackbar.make(viewDataBinding.root, R.string.unable_to_load_url, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupHashTagHandler() {
        viewDataBinding.content.popularHashTagsContainer.children.forEach { childChipView ->
            if (childChipView is Chip) {
                childChipView.setOnClickListener(chipClickListener)
            }
        }
    }

    /**
     * Internal listener to handle clicks from existing chips.
     */
    private val chipClickListener = View.OnClickListener { view ->
        if (view is Chip) {
            copyTextToClipboard(view.text.toString())
        }
    }

    private fun copyTextToClipboard(copyText: String) {
        analytics.logSelectItem(Analytics.CONTENT_TYPE_HASHTAG, copyText, copyText)
        val clipboardManager: ClipboardManager =
            ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)!!
        val clipData = ClipData.newPlainText("text", copyText)
        clipboardManager.setPrimaryClip(clipData)

        Snackbar.make(
            viewDataBinding.root,
            resources.getString(R.string.message_hashtag_copied_to_clipboard, copyText),
            Snackbar.LENGTH_LONG
        ).show()
    }

    //
    // Handle menu icons for about app and share app
    //
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.more_info_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_menu_about_app -> {
                Timber.d("About app menu item selected.")
                showAboutAppDialog()
                return true
            }
            R.id.toolbar_menu_share -> {
                Timber.d("Share app menu item selected.")
                Snackbar.make(
                    viewDataBinding.root,
                    "Thanks for caring! ❤️" +
                            "\nShare this App with friends?",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(R.string.button_cta_share_app, { startActivity(IntentBuilder.shareApp(resources)) })
                    .show()
                analytics.logEvent(Analytics.ACTION_SHARE_APP)

                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun showAboutAppDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setPositiveButton(R.string.button_cta_okay, null)
            .setView(R.layout.dialog_about_app)
            .show()
        activity?.let { analytics.logPageView(it, Analytics.SCREEN_ABOUT_APP) }
    }
}
