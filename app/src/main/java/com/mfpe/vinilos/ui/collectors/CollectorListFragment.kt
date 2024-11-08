package com.mfpe.vinilos.ui.collectors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mfpe.vinilos.adapters.CollectorAdapter
import com.mfpe.vinilos.databinding.FragmentCollectorListBinding
import com.mfpe.vinilos.viewmodel.CollectorListViewModel

class CollectorListFragment : Fragment() {

    private var _binding: FragmentCollectorListBinding? = null
    private val binding get() = _binding!!
    private lateinit var collectorAdapter: CollectorAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val collectorViewModel = ViewModelProvider(this)[CollectorListViewModel::class.java]

        _binding = FragmentCollectorListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        setupSearchView()

        collectorViewModel.collectors.observe(viewLifecycleOwner) { albums ->
            albums?.let {
                collectorAdapter.updateCollectors(it)
                binding.emptyView.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
                binding.recyclerCollectors.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }

        collectorViewModel.networkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

        collectorViewModel.fetchCollectors()
        return root
    }

    override fun onResume() {
        super.onResume()
        binding.searchBar.clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        Toast.makeText(activity, "Error when retrieving albums.", Toast.LENGTH_LONG).show()
    }

    private fun setupRecyclerView() {
        collectorAdapter = CollectorAdapter(emptyList())
        binding.recyclerCollectors.layoutManager = LinearLayoutManager(context)
        binding.recyclerCollectors.adapter = collectorAdapter
    }

    private fun setupSearchView() {
        val linearLayout1 = binding.searchBar.getChildAt(0) as LinearLayout
        val linearLayout2 = linearLayout1.getChildAt(2) as LinearLayout
        val linearLayout3 = linearLayout2.getChildAt(1) as LinearLayout
        val autoComplete = linearLayout3.getChildAt(0) as AutoCompleteTextView
        autoComplete.textSize = 14f

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                collectorAdapter.filter.filter(newText)
                return true
            }
        })
    }
}