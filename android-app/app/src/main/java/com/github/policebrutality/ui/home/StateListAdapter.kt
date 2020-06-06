package com.github.policebrutality.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.github.policebrutality.R
import com.github.policebrutality.databinding.ListItemStateBinding
import com.github.policebrutality.ui.common.DataBoundListAdapter

class StateListAdapter(
    private val itemClickCallback: ((ModelObject) -> Unit)?
) : DataBoundListAdapter<ModelObject, ListItemStateBinding>(
    diffCallback = object : DiffUtil.ItemCallback<ModelObject>() {
        override fun areItemsTheSame(oldItem: ModelObject, newItem: ModelObject): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ModelObject, newItem: ModelObject): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ListItemStateBinding {
        val binding = DataBindingUtil.inflate<ListItemStateBinding>(
            LayoutInflater.from(parent.context), R.layout.list_item_state,
            parent, false
        )

        binding.root.setOnClickListener {
            binding.data?.let {
                itemClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ListItemStateBinding, item: ModelObject) {
        binding.data = item
        binding.name.text = item.id
    }
}