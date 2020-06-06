package com.github.policebrutality.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.policebrutality.databinding.FragmentHomeBinding
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }
    private lateinit var viewDataBinding: FragmentHomeBinding
    private lateinit var adapter: StateListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@HomeFragment
            vm = viewModel
        }


        adapter = StateListAdapter {
            Timber.d("Tapped on state item $id")
        }
        adapter.submitList(emptyList())

        viewDataBinding.recyclerView.setHasFixedSize(false)
        viewDataBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewDataBinding.recyclerView.adapter = adapter


        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.states.observe(viewLifecycleOwner, Observer {
            Timber.d("Got states: $it")
            adapter.submitList(it.map { name -> ModelObject(name) })
        })
    }
}