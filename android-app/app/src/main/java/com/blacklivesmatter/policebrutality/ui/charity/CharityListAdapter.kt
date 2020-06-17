package com.blacklivesmatter.policebrutality.ui.charity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.blacklivesmatter.policebrutality.R
import com.blacklivesmatter.policebrutality.data.model.CharityOrg
import com.blacklivesmatter.policebrutality.databinding.ListItemCharityOrgBinding
import com.blacklivesmatter.policebrutality.ui.common.DataBoundListAdapter

class CharityListAdapter(
    private val itemClickCallback: ((CharityOrg) -> Unit)?
) : DataBoundListAdapter<CharityOrg, ListItemCharityOrgBinding>(
    diffCallback = object : DiffUtil.ItemCallback<CharityOrg>() {
        override fun areItemsTheSame(oldItem: CharityOrg, newItem: CharityOrg): Boolean {
            return oldItem.org_url == newItem.org_url
        }

        override fun areContentsTheSame(oldItem: CharityOrg, newItem: CharityOrg): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ListItemCharityOrgBinding {
        val binding = DataBindingUtil.inflate<ListItemCharityOrgBinding>(
            LayoutInflater.from(parent.context), R.layout.list_item_charity_org,
            parent, false
        )

        binding.root.setOnClickListener {
            binding.data?.let {
                itemClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ListItemCharityOrgBinding, item: CharityOrg) {
        binding.data = item
    }
}
