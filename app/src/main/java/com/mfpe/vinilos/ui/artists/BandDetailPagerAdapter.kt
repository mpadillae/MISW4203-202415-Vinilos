package com.mfpe.vinilos.ui.artists

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mfpe.vinilos.data.model.Album
import com.mfpe.vinilos.data.model.Musician
import com.mfpe.vinilos.ui.albums.AlbumListFragment

class BandDetailPagerAdapter(activity: BandDetailActivity,private val musicians:List<Musician>,private val albums: List<Album>):FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> MusicianListFragment(musicians)
            1 -> AlbumListFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}