package com.mfpe.vinilos.ui.artists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mfpe.vinilos.R
import com.mfpe.vinilos.adapters.MusicianAdapter
import com.mfpe.vinilos.data.model.Musician
import com.mfpe.vinilos.databinding.FragmentMusicianListBinding
import com.mfpe.vinilos.utils.GridSpacingItemDecoration
import com.mfpe.vinilos.viewmodel.ArtistListViewModel

class MusicianListFragment(

    private val musicians: List<Musician>? = null // Optional parameter
) : Fragment() {

    private var _binding: FragmentMusicianListBinding? = null
    private val binding get() = _binding!!

    private lateinit var musicianAdapter: MusicianAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val artistViewModel =
            ViewModelProvider(this)[ArtistListViewModel::class.java]

        _binding = FragmentMusicianListBinding.inflate(inflater, container, false)

        val root: View = binding.root

        setupRecyclerView()

        println("aaaa")
        println(musicians)

        if (musicians != null) {
            // Use the provided list of musicians
            musicianAdapter.updateMusicians(musicians)
            binding.emptyView.visibility = if (musicians.isNotEmpty()) View.GONE else View.VISIBLE
            binding.recyclerMusicians.visibility = if (musicians.isNotEmpty()) View.VISIBLE else View.GONE
        } else {
            // Fallback to fetching musicians from the ViewModel
            artistViewModel.networkError.observe(viewLifecycleOwner) { isNetworkError ->
                if (isNetworkError) onNetworkError()
            }

            artistViewModel.musicians.observe(viewLifecycleOwner) { fetchedMusicians ->
                fetchedMusicians?.let {
                    musicianAdapter.updateMusicians(it)
                    binding.emptyView.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
                    binding.recyclerMusicians.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
                }
            }

            artistViewModel.fetchMusicians()
        }

        return root
    }

    private fun setupRecyclerView() {
        musicianAdapter = MusicianAdapter(emptyList())
        binding.recyclerMusicians.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerMusicians.adapter = musicianAdapter
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.activity_custom_margin)
        val itemDecoration = GridSpacingItemDecoration(spacingInPixels)
        binding.recyclerMusicians.addItemDecoration(itemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        Toast.makeText(activity, "Error when retrieving Bands.", Toast.LENGTH_LONG).show()
    }
}