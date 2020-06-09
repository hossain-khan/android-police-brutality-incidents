package com.blacklivesmatter.policebrutality.ui.incidentlocations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blacklivesmatter.policebrutality.databinding.FragmentIncidentLocationsBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import timber.log.Timber

class LocationFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<LocationViewModel> { viewModelFactory }
    private lateinit var viewDataBinding: FragmentIncidentLocationsBinding
    private lateinit var adapter: LocationListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentIncidentLocationsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@LocationFragment
            vm = viewModel
        }

        adapter = LocationListAdapter { state ->
            Timber.d("Tapped on state item $state")
            findNavController().navigate(
                LocationFragmentDirections.navigationToIncidentsFragment(stateName = state.stateName)
            )
        }
        adapter.submitList(emptyList())

        viewDataBinding.recyclerView.setHasFixedSize(false)
        viewDataBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewDataBinding.recyclerView.adapter = adapter

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.locations.observe(viewLifecycleOwner, Observer { locationList ->
            Timber.d("Got locations: $locationList")
            adapter.submitList(locationList)
        })
    }
}
