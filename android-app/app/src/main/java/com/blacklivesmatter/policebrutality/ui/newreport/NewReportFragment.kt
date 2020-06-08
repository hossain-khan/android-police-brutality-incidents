package com.blacklivesmatter.policebrutality.ui.newreport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.blacklivesmatter.policebrutality.databinding.FragmentNewReportBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class NewReportFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<NewReportViewModel> { viewModelFactory }
    private lateinit var viewDataBinding: FragmentNewReportBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentNewReportBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@NewReportFragment
            vm = viewModel
        }

        return viewDataBinding.root
    }
}