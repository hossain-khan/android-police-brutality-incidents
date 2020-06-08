package com.blacklivesmatter.policebrutality.ui.common

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

/**
 * This is a duplicate of [DataBoundListAdapter] with some API changed to allow the adapter to adapt to
 * different view types in the same adapter.
 *
 * See [DataBoundListAdapter] for usage and documentation.
 */
abstract class DataBoundMultiViewListAdapter<T, V : ViewDataBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, DataBoundViewHolder<V>>(
    AsyncDifferConfig.Builder<T>(diffCallback).build()
) {
    /**
     * Provides [ViewDataBinding] for the list item after it's inflated with DataBinding.
     */
    protected abstract fun createBinding(parent: ViewGroup, viewType: Int): V

    /**
     * API where view should bound with the data model item.
     * @param binding The data-binding class with all the UI elements.
     * @param item Model item a the position (called by [onBindViewHolder]).
     * @param position The position of the item in the adapter.
     */
    protected abstract fun bind(binding: V, item: T, position: Int)

    /**
     * Forces the subclasses to provide view type for item and position that is used for [createBinding].
     */
    protected abstract fun getItemViewType(position: Int, item: T): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        val binding = createBinding(parent, viewType)
        return DataBoundViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {
        bind(holder.binding, getItem(position), position)
        holder.binding.executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int {
        return getItemViewType(position, getItem(position))
    }
}
