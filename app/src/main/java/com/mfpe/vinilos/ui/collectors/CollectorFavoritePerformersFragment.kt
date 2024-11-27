package com.mfpe.vinilos.ui.collectors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.mfpe.vinilos.adapters.CollectorFavoritePerformerAdapter
import com.mfpe.vinilos.data.model.Collector
import com.mfpe.vinilos.databinding.FragmentCollectorFavoritePerformersBinding


class CollectorFavoritePerformersFragment(private val collector: Collector) : Fragment() {

    private var _binding: FragmentCollectorFavoritePerformersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectorFavoritePerformersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        return root
    }

    private fun setupRecyclerView() {
        val performers = collector.favoritePerformers ?: emptyList()
        val collectorFavoritePerformerAdapter = CollectorFavoritePerformerAdapter(performers)
        binding.recyclerCollectorFavoritePerformers.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerCollectorFavoritePerformers.adapter = collectorFavoritePerformerAdapter
        binding.recyclerCollectorFavoritePerformers.visibility = if (performers.isEmpty()) View.GONE else View.VISIBLE
        binding.emptyView.visibility = if (performers.isEmpty()) View.VISIBLE else View.GONE
    }
}