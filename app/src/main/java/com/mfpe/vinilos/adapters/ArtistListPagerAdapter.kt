package com.mfpe.vinilos.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mfpe.vinilos.ui.albums.AlbumCommentsFragment
import com.mfpe.vinilos.ui.albums.AlbumDetailActivity
import com.mfpe.vinilos.ui.albums.AlbumInfoFragment
import com.mfpe.vinilos.ui.albums.AlbumTracksFragment
import com.mfpe.vinilos.ui.artists.ArtistListFragment
import com.mfpe.vinilos.ui.artists.BandListFragment
import com.mfpe.vinilos.ui.artists.MusicianListFragment

class ArtistListPagerAdapter(activity: ArtistListFragment) : FragmentStateAdapter(activity){

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MusicianListFragment()
            1 -> BandListFragment()

            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}