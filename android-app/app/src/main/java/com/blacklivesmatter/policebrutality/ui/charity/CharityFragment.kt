package com.blacklivesmatter.policebrutality.ui.charity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blacklivesmatter.policebrutality.R
import com.blacklivesmatter.policebrutality.analytics.Analytics
import com.blacklivesmatter.policebrutality.databinding.FragmentCharityDonateBinding
import com.blacklivesmatter.policebrutality.ui.extensions.observeKotlin
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CharityFragment : Fragment() {
    @Inject
    lateinit var analytics: Analytics

    private val viewModel by viewModels<CharityViewModel>()
    private lateinit var viewDataBinding: FragmentCharityDonateBinding
    private lateinit var adapter: CharityListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentCharityDonateBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@CharityFragment
            vm = viewModel
        }

        // This required to participate in providing toolbar menu on the host activity
        (requireActivity() as AppCompatActivity).setSupportActionBar(viewDataBinding.toolbar)

        adapter = CharityListAdapter { charity ->
            Timber.d("Tapped on charity $charity")
            analytics.logSelectItem(Analytics.CONTENT_TYPE_CHARITY, charity.org_url, charity.name)
        }
        adapter.submitList(emptyList())

        viewDataBinding.content.recyclerView.setHasFixedSize(false)
        viewDataBinding.content.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewDataBinding.content.recyclerView.adapter = adapter

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.charityList.observeKotlin(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.let { analytics.logPageView(it, Analytics.SCREEN_CHARITY_ORGANIZATIONS) }
    }

    //
    // Handle menu icons
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.charity_donate_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_menu_donate_info -> {
                Timber.d("Show disclaimer")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
