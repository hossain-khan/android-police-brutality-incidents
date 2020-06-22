package com.blacklivesmatter.policebrutality.ui.incidentlocations

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blacklivesmatter.policebrutality.R
import com.blacklivesmatter.policebrutality.analytics.Analytics
import com.blacklivesmatter.policebrutality.config.THE_846_DAY
import com.blacklivesmatter.policebrutality.databinding.FragmentIncidentLocationsBinding
import com.blacklivesmatter.policebrutality.ui.extensions.observeKotlin
import com.blacklivesmatter.policebrutality.ui.incidentlocations.LocationViewModel.NavigationEvent
import com.blacklivesmatter.policebrutality.ui.incidentlocations.LocationViewModel.RefreshEvent
import com.blacklivesmatter.policebrutality.ui.util.IncidentAvailabilityValidator
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.CompositeDateValidator
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.DateTimeUtils
import timber.log.Timber
import java.util.ArrayList
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class LocationFragment : Fragment() {
    @Inject
    lateinit var analytics: Analytics

    private val viewModel by viewModels<LocationViewModel>()
    private lateinit var viewDataBinding: FragmentIncidentLocationsBinding
    private lateinit var adapter: LocationListAdapter
    private var incidentDates: List<String> = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentIncidentLocationsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@LocationFragment
            vm = viewModel
        }

        // This required to participate in providing toolbar menu on the host activity
        (requireActivity() as AppCompatActivity).setSupportActionBar(viewDataBinding.toolbar)

        adapter = LocationListAdapter { state ->
            Timber.d("Tapped on state item $state")
            analytics.logSelectItem(Analytics.CONTENT_TYPE_LOCATION, state.stateName, state.stateName)
            findNavController().navigate(
                LocationFragmentDirections.navigationToIncidentsFragment(stateName = state.stateName)
            )
        }
        adapter.submitList(emptyList())
        showLoadingIndicator()

        viewDataBinding.recyclerView.setHasFixedSize(false)
        viewDataBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewDataBinding.recyclerView.adapter = adapter

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.locations.observeKotlin(viewLifecycleOwner) { locationList ->
            if (locationList.isEmpty().not()) {
                hideLoadingIndicator()
            }
            adapter.submitList(locationList)
        }

        viewModel.dateFilterEvent.observeKotlin(viewLifecycleOwner) { navigationEvent ->
            when (navigationEvent) {
                is NavigationEvent.Error -> {
                    Timber.d("There are no records, can't navigate")
                    Snackbar.make(
                        viewDataBinding.root,
                        getString(
                            R.string.message_no_incident_found_on_selected_date,
                            navigationEvent.selectedDateText
                        ),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is NavigationEvent.Filter -> {
                    Timber.d("Navigate incident list for $navigationEvent")
                    findNavController().navigate(
                        LocationFragmentDirections.navigationToIncidentsFragment(
                            timestamp = navigationEvent.timestamp,
                            dateText = navigationEvent.dateText
                        )
                    )
                }
            }
        }

        viewModel.incidentDates.observeKotlin(viewLifecycleOwner) { incidentDates ->
            // Saves the dates for use in date picker
            Timber.d("Received list of incident dates: $incidentDates")
            this.incidentDates = incidentDates
        }

        setupSwipeRefreshAction()
    }

    override fun onStart() {
        super.onStart()
        activity?.let { analytics.logPageView(it, Analytics.SCREEN_INCIDENT_LOCATION) }
    }

    private fun setupSwipeRefreshAction() {
        viewDataBinding.swipeRefresh.setColorSchemeResources(R.color.teal_700, R.color.blue_grey_500, R.color.teal_800)
        viewDataBinding.swipeRefresh.setOnRefreshListener {
            viewModel.onRefreshIncidentsRequested()
        }

        viewModel.refreshEvent.observeKotlin(viewLifecycleOwner) { event: RefreshEvent ->
            when (event) {
                is RefreshEvent.Success -> {
                    Timber.d("Refresh was successful")
                    hideLoadingIndicator()
                    Snackbar.make(
                        viewDataBinding.root,
                        getString(R.string.message_incident_refreshed, event.totalItems.toString()),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is RefreshEvent.Error -> {
                    Timber.d("Refresh was unsuccessful")
                    hideLoadingIndicator()
                    Snackbar.make(viewDataBinding.root, R.string.message_incident_refresh_fail, Snackbar.LENGTH_LONG)
                        .show()
                }
                is RefreshEvent.Loading -> {
                    showLoadingIndicator()
                }
            }
        }
    }

    private fun showLoadingIndicator() {
        viewDataBinding.swipeRefresh.isRefreshing = true
    }

    private fun hideLoadingIndicator() {
        viewDataBinding.swipeRefresh.isRefreshing = false
    }

    //
    // Handle menu icons for about app and share app
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.locations_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_menu_filter_by_date -> {
                showDatePicker()
                return true
            }
            R.id.toolbar_menu_refresh -> {
                viewModel.onRefreshIncidentsRequested()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showDatePicker() {
        val the846Day: Calendar = Calendar.getInstance()
        the846Day.timeInMillis = DateTimeUtils.toDate(THE_846_DAY.toInstant()).time
        the846Day.roll(Calendar.DATE, -1) // Give extra day in case something happened earlier

        val validators: MutableList<DateValidator> = ArrayList()
        validators.add(DateValidatorPointForward.from(the846Day.timeInMillis))
        validators.add(DateValidatorPointBackward.now())
        validators.add(IncidentAvailabilityValidator(incidentDates))

        val builder = MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setStart(the846Day.timeInMillis)
                    .setOpenAt(the846Day.timeInMillis)
                    .setEnd(System.currentTimeMillis())
                    .setValidator(CompositeDateValidator.allOf(validators))
                    .build()
            ).setTitleText(R.string.title_filter_incidents_by_date)

        val picker = builder.build()
        picker.addOnPositiveButtonClickListener { selectedTimeStamp ->
            viewModel.onDateTimeStampSelected(viewLifecycleOwner, selectedTimeStamp)
        }
        picker.show(childFragmentManager, picker.toString())
        activity?.let { analytics.logPageView(it, Analytics.SCREEN_INCIDENT_DATE_FILTER) }
    }
}
