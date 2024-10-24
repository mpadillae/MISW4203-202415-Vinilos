package com.mfpe.vinilos.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mfpe.vinilos.adapters.TrackAdapter
import com.mfpe.vinilos.databinding.FragmentAlbumTracksBinding
import com.mfpe.vinilos.data.model.Album
import com.mfpe.vinilos.utils.PrefsManager

class AlbumTracksFragment(private val album: Album) : Fragment() {

    private var _binding: FragmentAlbumTracksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumTracksBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        return root
    }

    private fun setupRecyclerView() {
        if (PrefsManager.getInstance(requireContext()).getUserType!! == "collector") {
            binding.addTrackButton.visibility = View.VISIBLE
            binding.addTrackButton.setOnClickListener {
                Toast.makeText(context, "Add track", Toast.LENGTH_SHORT).show()
            }
        }

        val trackAdapter = TrackAdapter(album.cover, album.tracks)
        binding.recyclerTracks.layoutManager = LinearLayoutManager(context)
        binding.recyclerTracks.adapter = trackAdapter
        binding.recyclerTracks.visibility = if (album.tracks.isEmpty()) View.GONE else View.VISIBLE
        binding.emptyView.visibility = if (album.tracks.isEmpty()) View.VISIBLE else View.GONE
    }
}