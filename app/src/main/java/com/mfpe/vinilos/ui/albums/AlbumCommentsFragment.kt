package com.mfpe.vinilos.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mfpe.vinilos.adapters.CommentAdapter
import com.mfpe.vinilos.databinding.FragmentAlbumCommentsBinding
import com.mfpe.vinilos.data.model.Album

class AlbumCommentsFragment(private val album: Album) : Fragment() {

    private var _binding: FragmentAlbumCommentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumCommentsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        return root
    }

    private fun setupRecyclerView() {
        val commentAdapter = CommentAdapter(album.comments ?: emptyList())
        binding.recyclerComments.layoutManager = LinearLayoutManager(context)
        binding.recyclerComments.adapter = commentAdapter
        val comments = album.comments ?: emptyList()
        binding.recyclerComments.visibility = if (comments.isEmpty()) View.GONE else View.VISIBLE
        binding.emptyView.visibility = if (comments.isEmpty()) View.VISIBLE else View.GONE
    }
}