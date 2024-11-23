package com.mfpe.vinilos.ui.artists

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mfpe.vinilos.data.model.Album
import com.mfpe.vinilos.ui.albums.AlbumListFragment

class MusicianDetailPagerActivity(activity: MusicianDetailActivity,private val albums: List<Album>):
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> AlbumListFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}