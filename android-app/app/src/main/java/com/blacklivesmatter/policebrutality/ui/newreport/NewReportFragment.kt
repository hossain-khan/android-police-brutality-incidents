package com.blacklivesmatter.policebrutality.ui.newreport

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.blacklivesmatter.policebrutality.R
import com.blacklivesmatter.policebrutality.analytics.Analytics
import com.blacklivesmatter.policebrutality.databinding.FragmentNewReportBinding
import com.blacklivesmatter.policebrutality.ui.extensions.observeKotlin
import com.blacklivesmatter.policebrutality.ui.util.IntentBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewReportFragment : Fragment() {
    @Inject
    lateinit var analytics: Analytics

    private val viewModel by viewModels<NewReportViewModel>()
    private lateinit var viewDataBinding: FragmentNewReportBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentNewReportBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@NewReportFragment
            vm = viewModel
        }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bindGuideText(viewDataBinding.content.incidentReportGuideText)

        viewModel.openReportIncidentUrl.observeKotlin(viewLifecycleOwner) { reportUrl ->
            val webUrlOpenIntent = IntentBuilder.build(requireContext(), reportUrl)

            if (webUrlOpenIntent != null) {
                startActivity(webUrlOpenIntent)
            } else {
                Snackbar
                    .make(viewDataBinding.root, R.string.unable_to_load_report_incident_url, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.let { analytics.logPageView(it, Analytics.SCREEN_REPORT_INCIDENT) }
    }

    private fun bindGuideText(textView: MaterialTextView) {
        val message = HtmlCompat.fromHtml(
            resources.getText(R.string.report_new_incident_guideline_text) as String,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.text = message
    }
}
