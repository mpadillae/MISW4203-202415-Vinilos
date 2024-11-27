package com.mfpe.vinilos.ui.albums

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mfpe.vinilos.adapters.TrackAdapter
import com.mfpe.vinilos.data.model.Album
import com.mfpe.vinilos.data.model.Track
import com.mfpe.vinilos.data.network.RetrofitApiClient
import com.mfpe.vinilos.data.network.TrackService
import com.mfpe.vinilos.databinding.FragmentAlbumTracksBinding
import com.mfpe.vinilos.utils.PrefsManager
import retrofit2.Call
import retrofit2.Response
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.util.Log
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
class AlbumTracksFragment(private val album: Album) : Fragment() {

    private var _binding: FragmentAlbumTracksBinding? = null
    private val binding get() = _binding!!

    private lateinit var trackReceiver: BroadcastReceiver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumTracksBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()

        trackReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == "com.mfpe.vinilos.TRACK_ADDED") {
                    val albumId = intent.getIntExtra("albumId", -1)
                    if (albumId == album.id) {
                        Log.d("AlbumTracksFragment", "Track agregado, recargando lista.")
                        fetchTracks(album.id)
                    }
                }
            }
        }
        val filter = IntentFilter("com.mfpe.vinilos.TRACK_ADDED")
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(trackReceiver, filter)

        return root
    }

    private fun setupRecyclerView() {
        if (PrefsManager.getInstance(requireContext()).getUserType!! == "collector") {
            binding.addTrackButton.visibility = View.VISIBLE
            binding.addTrackButton.setOnClickListener {
                val intent = Intent(requireContext(), AddTrackActivity::class.java)
                intent.putExtra("albumId", album.id)
                startActivity(intent)
            }
        }

        fetchTracks(album.id)
    }

    private fun fetchTracks(albumId: Int) {
        val trackService = RetrofitApiClient.createRetrofitService(TrackService::class.java)

        trackService.getTracks(albumId).enqueue(object : retrofit2.Callback<List<Track>> {
            override fun onResponse(call: Call<List<Track>>, response: Response<List<Track>>) {
                if (response.isSuccessful) {
                    val tracks = response.body() ?: emptyList()
                    Log.d("AlbumTracksFragment", "Tracks recibidos: $tracks")
                    updateRecyclerView(tracks)
                } else {
                    showError("Error al cargar los tracks.")
                }
            }

            override fun onFailure(call: Call<List<Track>>, t: Throwable) {
                showError("Error de red: ${t.message}")
            }
        })
    }

    private fun updateRecyclerView(tracks: List<Track>) {
        Log.d("AlbumTracksFragment", "Actualizando RecyclerView con ${tracks.size} tracks.")
        val trackAdapter = TrackAdapter(album.cover, tracks)

        binding.recyclerTracks.layoutManager = LinearLayoutManager(context)
        binding.recyclerTracks.adapter = trackAdapter

        binding.recyclerTracks.visibility = if (tracks.isEmpty()) View.GONE else View.VISIBLE
        binding.emptyView.visibility = if (tracks.isEmpty()) View.VISIBLE else View.GONE
    }
    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(trackReceiver)
        _binding = null
    }
}