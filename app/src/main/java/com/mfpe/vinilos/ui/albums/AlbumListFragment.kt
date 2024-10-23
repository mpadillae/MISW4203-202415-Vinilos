package com.mfpe.vinilos.ui.albums

import GridSpacingItemDecoration
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
import androidx.recyclerview.widget.GridLayoutManager
import com.mfpe.vinilos.R
import com.mfpe.vinilos.adapters.AlbumAdapter
import com.mfpe.vinilos.databinding.FragmentAlbumListBinding
import com.mfpe.vinilos.utils.PrefsManager
import com.mfpe.vinilos.viewmodels.AlbumListViewModel


class AlbumListFragment : Fragment() {

    private var _binding: FragmentAlbumListBinding? = null
    private val binding get() = _binding!!
    private lateinit var albumAdapter: AlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vm = ViewModelProvider(this)[AlbumListViewModel::class.java]

        _binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        setupSearchView()

        vm.albums.observe(viewLifecycleOwner) { albums ->
            albums?.let {
                albumAdapter.updateAlbums(it)
                binding.emptyView.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
                binding.recyclerAlbums.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }

        vm.networkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

        vm.fetchAlbums()
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
        if (PrefsManager.getInstance(requireContext()).getUserType!! == "collector") {
            binding.addAlbumButton.visibility = View.VISIBLE
            binding.addAlbumButton.setOnClickListener {
                Toast.makeText(context, "Add album", Toast.LENGTH_SHORT).show()
            }
        }

        albumAdapter = AlbumAdapter(emptyList())
        binding.recyclerAlbums.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerAlbums.adapter = albumAdapter
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.activity_custom_margin)
        val itemDecoration = GridSpacingItemDecoration(2, spacingInPixels)
        binding.recyclerAlbums.addItemDecoration(itemDecoration)
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
                albumAdapter.filter.filter(newText)
                return true
            }
        })
    }

}