package com.mfpe.vinilos.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mfpe.vinilos.databinding.FragmentAlbumInfoBinding
import com.mfpe.vinilos.data.model.Album
import java.text.SimpleDateFormat
import java.util.Locale

class AlbumInfoFragment(private val album: Album) : Fragment() {

    private var _binding: FragmentAlbumInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.albumInfoGenre.text = album.genre
        binding.albumInfoReleasedate.text = SimpleDateFormat("dd/MM/yyyy", Locale.US).format(album.releaseDate)
        binding.albumInfoRecord.text = album.recordLabel
        binding.albumInfoArtist.text = "  •  ${album.performers?.getOrNull(0)?.name ?: "Autor desconocido"}"

        return root
    }
}