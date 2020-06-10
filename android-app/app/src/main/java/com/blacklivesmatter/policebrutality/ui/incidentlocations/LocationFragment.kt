package com.blacklivesmatter.policebrutality.ui.incidentlocations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blacklivesmatter.policebrutality.R
import com.blacklivesmatter.policebrutality.config.THE_846_DAY
import com.blacklivesmatter.policebrutality.databinding.FragmentIncidentLocationsBinding
import com.blacklivesmatter.policebrutality.ui.extensions.observeKotlin
import com.blacklivesmatter.policebrutality.ui.incidentlocations.LocationViewModel.NavigationEvent
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.CompositeDateValidator
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import org.threeten.bp.DateTimeUtils
import timber.log.Timber
import java.util.ArrayList
import java.util.Calendar
import javax.inject.Inject

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

        // This required to participate in providing toolbar menu on the host activity
        (requireActivity() as AppCompatActivity).setSupportActionBar(viewDataBinding.toolbar)

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

        viewModel.locations.observeKotlin(viewLifecycleOwner) { locationList ->
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

        val builder = MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setStart(the846Day.timeInMillis)
                    .setValidator(CompositeDateValidator.allOf(validators))
                    .build()
            ).setTitleText(R.string.title_filter_incidents_by_date)

        val picker = builder.build()
        picker.addOnPositiveButtonClickListener { selectedTimeStamp ->
            viewModel.onDateTimeStampSelected(viewLifecycleOwner, selectedTimeStamp)
        }
        picker.show(childFragmentManager, picker.toString())
    }
}
