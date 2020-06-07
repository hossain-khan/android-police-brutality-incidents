package com.github.policebrutality.ui.incident

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.github.policebrutality.R
import com.github.policebrutality.data.model.Incident
import com.github.policebrutality.databinding.ListItemIncidentCoreBinding
import com.github.policebrutality.ui.common.DataBoundListAdapter

class IncidentsAdapter(
    private val itemClickCallback: ((Incident) -> Unit)?
) : DataBoundListAdapter<Incident, ListItemIncidentCoreBinding>(
    diffCallback = object : DiffUtil.ItemCallback<Incident>() {
        override fun areItemsTheSame(oldItem: Incident, newItem: Incident): Boolean {
            return oldItem.id == newItem.id
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
    }
}