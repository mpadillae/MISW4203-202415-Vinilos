package com.mfpe.vinilos.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mfpe.vinilos.data.model.Collector
import com.mfpe.vinilos.ui.collectors.CollectorAlbumsFragment
import com.mfpe.vinilos.ui.collectors.CollectorCommentsFragment
import com.mfpe.vinilos.ui.collectors.CollectorFavoritePerformersFragment

class CollectorDetailPagerAdapter(activity: FragmentActivity, private val collector: Collector) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CollectorFavoritePerformersFragment(collector)
            1 -> CollectorAlbumsFragment(collector)
            2 -> CollectorCommentsFragment(collector)
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }

}