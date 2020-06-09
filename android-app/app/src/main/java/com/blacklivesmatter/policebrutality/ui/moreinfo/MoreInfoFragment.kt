package com.blacklivesmatter.policebrutality.ui.moreinfo

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.blacklivesmatter.policebrutality.R
import com.blacklivesmatter.policebrutality.databinding.FragmentMoreInfoBinding
import com.blacklivesmatter.policebrutality.ui.extensions.observeKotlin
import com.blacklivesmatter.policebrutality.ui.util.IntentBuilder
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MoreInfoFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MoreInfoViewModel> { viewModelFactory }
    private lateinit var viewDataBinding: FragmentMoreInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentMoreInfoBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@MoreInfoFragment
            vm = viewModel
        }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupHashTagHandler()
        handleExternalUrl()
    }

    private fun handleExternalUrl() {
        viewModel.openExternalUrl.observeKotlin(viewLifecycleOwner) { url ->
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
}
