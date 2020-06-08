package com.blacklivesmatter.policebrutality.ui.common

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

/**
 * The offset decorator for the item in the recyclerview.
 */
class ItemOffsetDecoration(@NonNull context: Context, @DimenRes itemOffsetId: Int) : RecyclerView.ItemDecoration() {

    private var itemOffset: Int = context.resources.getDimensionPixelOffset(itemOffsetId)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(itemOffset, itemOffset, itemOffset, itemOffset)
    }
}
