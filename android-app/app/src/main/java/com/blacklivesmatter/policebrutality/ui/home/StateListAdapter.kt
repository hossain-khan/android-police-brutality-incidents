package com.blacklivesmatter.policebrutality.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.blacklivesmatter.policebrutality.R
import com.blacklivesmatter.policebrutality.data.model.LocationIncidents
import com.blacklivesmatter.policebrutality.databinding.ListItemLocationBinding
import com.blacklivesmatter.policebrutality.ui.common.DataBoundListAdapter

class StateListAdapter(
    private val itemClickCallback: ((LocationIncidents) -> Unit)?
) : DataBoundListAdapter<LocationIncidents, ListItemLocationBinding>(
    diffCallback = object : DiffUtil.ItemCallback<LocationIncidents>() {
        override fun areItemsTheSame(oldItem: LocationIncidents, newItem: LocationIncidents): Boolean {
            return oldItem.stateName == newItem.stateName
        }

        override fun areContentsTheSame(oldItem: LocationIncidents, newItem: LocationIncidents): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ListItemLocationBinding {
        val binding = DataBindingUtil.inflate<ListItemLocationBinding>(
            LayoutInflater.from(parent.context), R.layout.list_item_location,
            parent, false
        )

        binding.root.setOnClickListener {
            binding.data?.let {
                itemClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ListItemLocationBinding, item: LocationIncidents) {
        binding.data = item
    }
}