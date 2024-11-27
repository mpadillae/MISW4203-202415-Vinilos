package com.mfpe.vinilos.ui.collectors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mfpe.vinilos.adapters.CollectorAlbumAdapter
import com.mfpe.vinilos.data.model.Collector
import com.mfpe.vinilos.databinding.FragmentCollectorAlbumsBinding
import com.mfpe.vinilos.viewmodel.CollectorListViewModel


class CollectorAlbumsFragment(private val collector: Collector) : Fragment() {

    private var _binding: FragmentCollectorAlbumsBinding? = null
    private val binding get() = _binding!!
    private lateinit var collectorFavoritePerformerAdapter: CollectorAlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val collectorViewModel = ViewModelProvider(this)[CollectorListViewModel::class.java]

        _binding = FragmentCollectorAlbumsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()

        collectorViewModel.collectorAlbums.observe(viewLifecycleOwner) { albums ->
            albums?.let {
                collectorFavoritePerformerAdapter.updateCollectorAlbums(it)
                binding.emptyView.visibility = if (albums.isEmpty()) View.VISIBLE else View.GONE
                binding.recyclerCollectorAlbums.visibility = if (albums.isEmpty()) View.GONE else View.VISIBLE
            }
        }

        collectorViewModel.networkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

        collectorViewModel.fetchCollectorAlbums(collector.id)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        Toast.makeText(activity, "Error when retrieving collector albums.", Toast.LENGTH_LONG).show()
    }

    private fun setupRecyclerView() {
        collectorFavoritePerformerAdapter = CollectorAlbumAdapter(emptyList())
        binding.recyclerCollectorAlbums.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerCollectorAlbums.adapter = collectorFavoritePerformerAdapter
    }
}