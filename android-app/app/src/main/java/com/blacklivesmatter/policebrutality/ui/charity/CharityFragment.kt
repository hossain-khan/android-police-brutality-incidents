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
import com.blacklivesmatter.policebrutality.ui.util.IntentBuilder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
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

        // Observes fragment lifecycle events to handle lifecycle specific events
        lifecycle.addObserver(viewModel)

        // This required to participate in providing toolbar menu on the host activity
        (requireActivity() as AppCompatActivity).setSupportActionBar(viewDataBinding.toolbar)

        adapter = CharityListAdapter(itemClickCallback = { charity ->
            viewModel.onCharitySelected(charity)
            startActivity(IntentBuilder.build(requireContext(), charity.org_url))
        }, donateNowCallback = { donateCharity ->
            viewModel.onCharitySelected(donateCharity)
            analytics.logEvent(Analytics.ACTION_CHARITY_DONATE)
            startActivity(IntentBuilder.build(requireContext(), donateCharity.donate_url))
        })
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

        viewModel.shouldShowCharityDisclaimerInfoMessage.observeKotlin(viewLifecycleOwner) {
            Snackbar.make(
                viewDataBinding.root,
                R.string.message_charity_donation_landing_first_time_info,
                Snackbar.LENGTH_INDEFINITE
            ).setAction(R.string.button_cta_thanks, {}).show()
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
                analytics.logEvent(Analytics.ACTION_CHARITY_DONATE_INFO)
                MaterialAlertDialogBuilder(requireContext())
                    .setIcon(R.drawable.ic_outline_warn_info_24)
                    .setTitle(R.string.title_dialog_disclaimer)
                    .setMessage(R.string.message_charity_list_not_affiliated_with_app)
                    .setPositiveButton(R.string.button_cta_okay, null)
                    .show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
