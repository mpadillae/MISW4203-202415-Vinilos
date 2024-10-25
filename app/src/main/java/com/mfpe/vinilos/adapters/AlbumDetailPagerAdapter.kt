package com.mfpe.vinilos.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mfpe.vinilos.data.model.Album
import com.mfpe.vinilos.ui.albums.AlbumCommentsFragment
import com.mfpe.vinilos.ui.albums.AlbumInfoFragment
import com.mfpe.vinilos.ui.albums.AlbumTracksFragment

class AlbumDetailPagerAdapter(activity: FragmentActivity, private val album: Album) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AlbumInfoFragment(album)
            1 -> AlbumTracksFragment(album)
            2 -> AlbumCommentsFragment(album)
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }

}