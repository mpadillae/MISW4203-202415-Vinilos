package com.mfpe.vinilos.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        outRect.left = spacing / 2
        outRect.right = spacing / 2

        // Do not change the vertical spacing
        outRect.top = 0
        outRect.bottom = 4
    }
}