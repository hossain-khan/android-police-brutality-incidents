package com.blacklivesmatter.policebrutality.ui.incident

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.blacklivesmatter.policebrutality.R
import com.blacklivesmatter.policebrutality.analytics.Analytics
import com.blacklivesmatter.policebrutality.analytics.Analytics.Companion.CONTENT_TYPE_INCIDENT_SHARE
import com.blacklivesmatter.policebrutality.data.model.Incident
import com.blacklivesmatter.policebrutality.databinding.FragmentIncidentsBinding
import com.blacklivesmatter.policebrutality.ui.extensions.observeKotlin
import com.blacklivesmatter.policebrutality.ui.incident.arg.FilterType
import com.blacklivesmatter.policebrutality.ui.util.IntentBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

/**
 * Shows list of incidents that happened during the peaceful protest.
 */
@AndroidEntryPoint
class IncidentsFragment : Fragment() {
    @Inject
    lateinit var analytics: Analytics

    private val viewModel by viewModels<IncidentViewModel>()
    private lateinit var viewDataBinding: FragmentIncidentsBinding
    private val navArgs: IncidentsFragmentArgs by navArgs()
    private lateinit var adapter: IncidentsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentIncidentsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@IncidentsFragment
            vm = viewModel
        }

        viewModel.setArgs(navArgs)

        adapter = IncidentsAdapter(
            incidentFilterType = navArgs.filterArgs.type,
            itemClickCallback = { clickedIncident ->
                Timber.d("Selected Incident: $clickedIncident")
                analytics.logSelectItem(
                    type = Analytics.CONTENT_TYPE_INCIDENT,
                    id = clickedIncident.id,
                    name = clickedIncident.incident_id ?: "---"
                )
                showIncidentDetailsForSharing(clickedIncident)
            }, linkClickCallback = { clickedLink ->
                openWebPage(clickedLink)
            }
        )

        viewDataBinding.recyclerView.setHasFixedSize(false)
        viewDataBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewDataBinding.recyclerView.adapter = adapter

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewDataBinding.toolbar.title = getString(navArgs.titleResId(), navArgs.titleText())
        viewDataBinding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.incidents.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.shareIncident.observeKotlin(viewLifecycleOwner) { incident ->
            dismissDetailsDialogIfShowing()

            analytics.logSelectItem(CONTENT_TYPE_INCIDENT_SHARE, incident.id, incident.incident_id ?: "---")
            startActivity(IntentBuilder.share(incident))
        }

        viewModel.shouldShowShareCapabilityMessage.observeKotlin(viewLifecycleOwner) {
            Snackbar.make(
                viewDataBinding.root,
                R.string.message_share_incident_capability,
                Snackbar.LENGTH_INDEFINITE
            ).setAction(R.string.button_cta_thanks, {}).show()
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.let { analytics.logPageView(it, navArgs.screenName()) }
    }

    private fun showIncidentDetailsForSharing(incident: Incident) {
        Timber.d("User tapped on the incident item. Show details and allow sharing.")

        val dialog = IncidentDetailsBottomSheetFragment()
        dialog.setData(viewModel, incident)
        dialog.show(childFragmentManager, IncidentDetailsBottomSheetFragment.FRAGMENT_TAG)
    }

    private fun dismissDetailsDialogIfShowing() {
        val dialog = childFragmentManager.findFragmentByTag(IncidentDetailsBottomSheetFragment.FRAGMENT_TAG)
        if (dialog != null && dialog is IncidentDetailsBottomSheetFragment) {
            if (dialog.isVisible) {
                dialog.dismiss()
            }
        }
    }

    /**
     * Opens external web URL
     * See: https://developer.android.com/guide/components/intents-common#ViewUrl
     */
    private fun openWebPage(url: String) {
        val intent = IntentBuilder.build(requireContext(), url)
        if (intent != null) {
            startActivity(intent)
        } else {
            Snackbar.make(viewDataBinding.root, R.string.unable_to_load_url, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun IncidentsFragmentArgs.titleResId(): Int {
        return when (filterArgs.type) {
            FilterType.STATE -> R.string.title_incidents_at_location
            FilterType.DATE -> R.string.title_incidents_on_date
            FilterType.LATEST -> R.string.title_incidents_most_recent
        }
    }

    private fun IncidentsFragmentArgs.titleText(): String {
        return when (filterArgs.type) {
            FilterType.STATE -> filterArgs.stateName!!
            FilterType.DATE -> filterArgs.dateText!!
            FilterType.LATEST -> ""
        }
    }

    private fun IncidentsFragmentArgs.screenName(): String {
        return when (filterArgs.type) {
            FilterType.STATE -> Analytics.SCREEN_INCIDENT_LIST_BY_LOCATION
            FilterType.DATE -> Analytics.SCREEN_INCIDENT_LIST_BY_DATE
            FilterType.LATEST -> Analytics.SCREEN_INCIDENT_LIST_MOST_RECENT
        }
    }
}
