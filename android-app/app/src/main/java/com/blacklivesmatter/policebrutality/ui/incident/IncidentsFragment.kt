package com.blacklivesmatter.policebrutality.ui.incident

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.blacklivesmatter.policebrutality.R
import com.blacklivesmatter.policebrutality.analytics.Analytics
import com.blacklivesmatter.policebrutality.databinding.FragmentIncidentsBinding
import com.blacklivesmatter.policebrutality.ui.util.IntentBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

/**
 * Shows list of incidents that happened during the peaceful protest.
 */
class IncidentsFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var analytics: Analytics

    private val viewModel by viewModels<IncidentViewModel> { viewModelFactory }
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
            isDateBasedIncidents = navArgs.isDateBased(),
            itemClickCallback = { clickedIncident ->
                Timber.d("Selected Incident: $clickedIncident")
                analytics.logSelectItem(
                    type = Analytics.CONTENT_TYPE_INCIDENT,
                    id = clickedIncident.id,
                    name = clickedIncident.incident_id ?: "---"
                )
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
    }

    override fun onStart() {
        super.onStart()
        activity?.let {
            analytics.logPageView(
                it, if (navArgs.isDateBased()) Analytics.SCREEN_INCIDENT_LIST_BY_DATE
                else Analytics.SCREEN_INCIDENT_LIST_BY_LOCATION
            )
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

    private fun IncidentsFragmentArgs.isDateBased(): Boolean = navArgs.timestamp != 0L
    private fun IncidentsFragmentArgs.titleResId(): Int =
        if (isDateBased()) R.string.title_incidents_on_date else R.string.title_incidents_at_location

    private fun IncidentsFragmentArgs.titleText(): String = if (isDateBased()) dateText!! else stateName!!
}
