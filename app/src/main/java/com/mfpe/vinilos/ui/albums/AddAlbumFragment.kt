package com.mfpe.vinilos.ui.albums

import android.os.Bundle
import android.widget.*
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mfpe.vinilos.viewmodel.AlbumListViewModel
import android.graphics.Color
import com.mfpe.vinilos.data.model.Album
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.fragment.app.Fragment
import com.mfpe.vinilos.databinding.FragmentAddAlbumBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

class AddAlbumFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etUrlCover: EditText
    private lateinit var spGenre: Spinner
    private lateinit var etReleaseDate: EditText
    private lateinit var spRecordLabel: Spinner
    private lateinit var etDescription: EditText
    private lateinit var btnAddAlbum: Button
    private lateinit var albumListViewModel: AlbumListViewModel
    private var _binding: FragmentAddAlbumBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAlbumBinding.inflate(inflater, container, false)
        val view = binding.root

        albumListViewModel = ViewModelProvider(this)[AlbumListViewModel::class.java]

        etName = binding.etName
        etUrlCover = binding.etUrlCover
        spGenre = binding.spGenre
        etReleaseDate = binding.etReleaseDate
        spRecordLabel = binding.spRecordLabel
        etDescription = binding.etDescription
        btnAddAlbum = binding.btnAddAlbum

        setupSpinners()

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        btnAddAlbum.setOnClickListener {
            if (validateFields()) {
                val releaseDateString = etReleaseDate.text.toString()


                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val releaseDate: Date? = try {
                    dateFormat.parse(releaseDateString)
                } catch (e: Exception) {
                    null
                }

                if (releaseDate != null) {
                    val album = Album(
                        name = etName.text.toString(),
                        cover = etUrlCover.text.toString(),
                        genre = spGenre.selectedItem.toString(),
                        releaseDate = releaseDate,
                        recordLabel = spRecordLabel.selectedItem.toString(),
                        description = etDescription.text.toString()
                    )

                    albumListViewModel.addAlbum(album) { success ->
                        if (success) {
                            Toast.makeText(requireContext(), "Álbum agregado exitosamente", Toast.LENGTH_LONG).show()
                            findNavController().popBackStack()
                        } else {
                            Toast.makeText(requireContext(), "Error al agregar el álbum", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Fecha inválida", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view

    }



    private fun isValidUrlCover(url: String): Boolean {
        val regex = "^https://.*\\.(jpg|jpeg|png|gif|bmp|webp)$".toRegex()
        return url.matches(regex)
    }


    private fun setupSpinners() {

        val genres = listOf("Seleccione un género", "Classical", "Salsa", "Rock", "Folk")
        val recordLabels = listOf("Seleccione una disquera", "Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")

        val genreAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genres)
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spGenre.adapter = genreAdapter
        spGenre.setSelection(0)

        val recordLabelAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, recordLabels)
        recordLabelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spRecordLabel.adapter = recordLabelAdapter
        spRecordLabel.setSelection(0)
    }

    private fun validateFields(): Boolean {
        var isValid = true

        if (etName.text.isEmpty()) {
            etName.error = "Este campo es obligatorio"
            isValid = false
        }

        if (etReleaseDate.text.isEmpty() || !etReleaseDate.text.toString().matches(Regex("\\d{2}/\\d{2}/\\d{4}"))) {
            etReleaseDate.error = "Fecha inválida, use el formato dd/mm/aaaa"
            isValid = false
        }
        if (spGenre.selectedItem == "Seleccione un género") {
            val errorText = spGenre.selectedView as TextView
            errorText.error = ""
            errorText.text = "Seleccione un género"
            isValid = false
        } else {
            val errorText = spGenre.selectedView as TextView
            errorText.setTextColor(Color.BLACK)
        }

        if (spRecordLabel.selectedItem == "Seleccione una disquera") {
            val errorText = spRecordLabel.selectedView as TextView
            errorText.error = ""
            errorText.text = "Seleccione una disquera"
            isValid = false
        } else {
            val errorText = spRecordLabel.selectedView as TextView
            errorText.setTextColor(Color.BLACK)
        }

        if (etDescription.text.isEmpty()) {
            etDescription.error = "Este campo es obligatorio"
            isValid = false
        }

        if (etUrlCover.text.isEmpty()) {
            etUrlCover.error = "Este campo es obligatorio"
            isValid = false
        } else if (!isValidUrlCover(etUrlCover.text.toString())) {
            etUrlCover.error = "URL de la portada no válida. Asegúrate de que sigue el formato correcto."
            isValid = false
        }

        return isValid
    }

}