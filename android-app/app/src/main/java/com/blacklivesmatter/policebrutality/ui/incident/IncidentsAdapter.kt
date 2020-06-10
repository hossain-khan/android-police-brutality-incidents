package com.blacklivesmatter.policebrutality.ui.incident

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.blacklivesmatter.policebrutality.R
import com.blacklivesmatter.policebrutality.data.model.Incident
import com.blacklivesmatter.policebrutality.databinding.ListItemIncidentCoreBinding
import com.blacklivesmatter.policebrutality.ui.common.DataBoundListAdapter

class IncidentsAdapter constructor(
    private val isDateBasedIncidents: Boolean,
    private val itemClickCallback: ((Incident) -> Unit)?,
    private val linkClickCallback: ((String) -> Unit)? = null
) : DataBoundListAdapter<Incident, ListItemIncidentCoreBinding>(
    diffCallback = object : DiffUtil.ItemCallback<Incident>() {
        override fun areItemsTheSame(oldItem: Incident, newItem: Incident): Boolean {
            return oldItem.incident_id == newItem.incident_id
        }

        override fun areContentsTheSame(oldItem: Incident, newItem: Incident): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ListItemIncidentCoreBinding {
        val binding = DataBindingUtil.inflate<ListItemIncidentCoreBinding>(
            LayoutInflater.from(parent.context), R.layout.list_item_incident_core,
            parent, false
        )

        binding.root.setOnClickListener {
            binding.incident?.let {
                itemClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ListItemIncidentCoreBinding, item: Incident) {
        binding.incident = item
        binding.isDateBased = isDateBasedIncidents

        val adapter = IncidentLinkAdapter(itemClickCallback = linkClickCallback)
        binding.linksRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.linksRecyclerView.setHasFixedSize(true)
        binding.linksRecyclerView.adapter = adapter
        adapter.submitList(item.links)
    }
}
