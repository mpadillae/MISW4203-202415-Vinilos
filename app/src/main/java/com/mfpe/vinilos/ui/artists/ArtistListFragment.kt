package com.mfpe.vinilos.ui.artists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.mfpe.vinilos.adapters.AlbumDetailPagerAdapter
import com.mfpe.vinilos.adapters.ArtistListPagerAdapter
import com.mfpe.vinilos.data.model.Album
import com.mfpe.vinilos.databinding.FragmentArtistListBinding
import com.mfpe.vinilos.viewmodel.ArtistListViewModel

class ArtistListFragment : Fragment() {

    private var _binding: FragmentArtistListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val artistViewModel =
            ViewModelProvider(this)[ArtistListViewModel::class.java]

        _binding = FragmentArtistListBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val adapter = ArtistListPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Musicos"
                1 -> "Bandas"
                else -> null
            }
        }.attach()

        val textView: TextView = binding.textDashboard

        artistViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}