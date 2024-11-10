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
import com.mfpe.vinilos.adapters.BandAdapter
import com.mfpe.vinilos.databinding.FragmentBandListBinding
import com.mfpe.vinilos.utils.GridSpacingItemDecoration
import com.mfpe.vinilos.viewmodel.ArtistListViewModel

class BandListFragment: Fragment() {

    private var _binding: FragmentBandListBinding?=null
    private val binding get() = _binding!!

    private lateinit var bandAdapter: BandAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val artistViewModel =
            ViewModelProvider(this)[ArtistListViewModel::class.java]

        _binding = FragmentBandListBinding.inflate(inflater, container, false)

        val root: View = binding.root

        setupRecyclerView()

        artistViewModel.networkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

        artistViewModel.bands.observe(viewLifecycleOwner){ bands->
            bands?.let {
                bandAdapter.updateBands(it)
                binding.emptyView.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
                binding.recyclerBands.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }
        artistViewModel.fetchBands()

        return root
    }

    private fun setupRecyclerView() {

        bandAdapter = BandAdapter(emptyList())
        binding.recyclerBands.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerBands.adapter = bandAdapter
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.activity_custom_margin)
        val itemDecoration = GridSpacingItemDecoration(spacingInPixels)
        binding.recyclerBands.addItemDecoration(itemDecoration)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        Toast.makeText(activity, "Error when retrieving Bands.", Toast.LENGTH_LONG).show()
    }
}