package com.blacklivesmatter.policebrutality.ui.moreinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.blacklivesmatter.policebrutality.databinding.FragmentMoreInfoBinding
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
}