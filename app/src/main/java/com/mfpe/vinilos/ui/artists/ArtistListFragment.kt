package com.mfpe.vinilos.ui.artists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mfpe.vinilos.adapters.ArtistListPagerAdapter
import com.mfpe.vinilos.databinding.FragmentArtistListBinding

class ArtistListFragment : Fragment() {

    private var _binding: FragmentArtistListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentArtistListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = ArtistListPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Músicos"
                1 -> "Bandas"
                else -> null
            }
        }.attach()


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}