package com.assignment.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView

/**
 * A [RecyclerView.ItemDecoration]
 */
class ItemDivider(
    @Px private val verticalSpace: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = verticalSpace
        if (parent.getChildAdapterPosition(view) == state.itemCount - 1)
            outRect.bottom = verticalSpace
    }
}
