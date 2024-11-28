package com.mfpe.vinilos.ui.collectors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mfpe.vinilos.adapters.CommentAdapter
import com.mfpe.vinilos.data.model.Collector
import com.mfpe.vinilos.databinding.FragmentCollectorCommentsBinding

class CollectorCommentsFragment(private val collector: Collector) : Fragment() {

    private var _binding: FragmentCollectorCommentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectorCommentsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        return root
    }

    private fun setupRecyclerView() {
        val comments = collector.comments ?: emptyList()
        val commentAdapter = CommentAdapter(comments)
        binding.recyclerComments.layoutManager = LinearLayoutManager(context)
        binding.recyclerComments.adapter = commentAdapter
        binding.recyclerComments.visibility = if (comments.isEmpty()) View.GONE else View.VISIBLE
        binding.emptyView.visibility = if (comments.isEmpty()) View.VISIBLE else View.GONE
    }
}