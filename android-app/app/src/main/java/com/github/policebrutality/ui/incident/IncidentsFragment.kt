package com.github.policebrutality.ui.incident

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.policebrutality.databinding.FragmentIncidentBinding
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class IncidentsFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<IncidentViewModel> { viewModelFactory }
    private lateinit var viewDataBinding: FragmentIncidentBinding
    private val navArgs: IncidentsFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentIncidentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@IncidentsFragment
            vm = viewModel
        }

        viewModel.selectedSate(navArgs.stateName)

        viewDataBinding.recyclerView.setHasFixedSize(false)
        viewDataBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //viewDataBinding.recyclerView.adapter = adapter


        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.let { actionBar ->
            actionBar.title = navArgs.stateName
        }

        viewModel.incidents.observe(viewLifecycleOwner, Observer {
            Timber.d("Got incidents: $it")
            //adapter.submitList(it.map { name -> State(name) })
        })
    }
}