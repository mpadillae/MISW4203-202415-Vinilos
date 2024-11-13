package com.mfpe.vinilos.ui.albums

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mfpe.vinilos.R
import com.mfpe.vinilos.data.model.requests.CreateAlbumRequest
import com.mfpe.vinilos.databinding.FragmentAddAlbumBinding
import com.mfpe.vinilos.viewmodel.AlbumListViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddAlbumFragment : Fragment() {

    private lateinit var albumListViewModel: AlbumListViewModel
    private var _binding: FragmentAddAlbumBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAlbumBinding.inflate(inflater, container, false)
        val view = binding.root

        albumListViewModel = ViewModelProvider(this)[AlbumListViewModel::class.java]

        setupSpinners()

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnAddAlbum.setOnClickListener {
            if (validateFields()) {
                val releaseDateString = binding.etReleaseDate.text.toString()


                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val releaseDate: Date? = try {
                    dateFormat.parse(releaseDateString)
                } catch (e: Exception) {
                    null
                }

                if (releaseDate != null) {
                    val createAlbumRequest = CreateAlbumRequest(
                        name = binding.etName.text.toString(),
                        cover = binding.etUrlCover.text.toString(),
                        genre = binding.spGenre.selectedItem.toString(),
                        releaseDate = releaseDate,
                        recordLabel = binding.spRecordLabel.selectedItem.toString(),
                        description = binding.etDescription.text.toString()
                    )

                    albumListViewModel.addAlbum(createAlbumRequest) { success ->
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
        binding.spGenre.adapter = genreAdapter
        binding.spGenre.setSelection(0)

        val recordLabelAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, recordLabels)
        recordLabelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spRecordLabel.adapter = recordLabelAdapter
        binding.spRecordLabel.setSelection(0)
    }

    private fun validateFields(): Boolean {
        var isValid = true

        if (binding.etName.text.isEmpty()) {
            binding.etName.error = "Este campo es obligatorio"
            isValid = false
        }

        if (binding.etReleaseDate.text.isEmpty() || !binding.etReleaseDate.text.toString().matches(Regex("\\d{2}/\\d{2}/\\d{4}"))) {
            binding.etReleaseDate.error = "Fecha inválida, use el formato dd/mm/aaaa"
            isValid = false
        }
        if (binding.spGenre.selectedItem == "Seleccione un género") {
            val errorText = binding.spGenre.selectedView as TextView
            errorText.error = ""
            errorText.text = resources.getString(R.string.selectGenreText)
            isValid = false
        } else {
            val errorText = binding.spGenre.selectedView as TextView
            errorText.setTextColor(Color.BLACK)
        }

        if (binding.spRecordLabel.selectedItem == "Seleccione una disquera") {
            val errorText = binding.spRecordLabel.selectedView as TextView
            errorText.error = ""
            errorText.text = resources.getString(R.string.selectDisqueraText)
            isValid = false
        } else {
            val errorText = binding.spRecordLabel.selectedView as TextView
            errorText.setTextColor(Color.BLACK)
        }

        if (binding.etDescription.text.isEmpty()) {
            binding.etDescription.error = "Este campo es obligatorio"
            isValid = false
        }

        if (binding.etUrlCover.text.isEmpty()) {
            binding.etUrlCover.error = "Este campo es obligatorio"
            isValid = false
        } else if (!isValidUrlCover(binding.etUrlCover.text.toString())) {
            binding.etUrlCover.error = "URL de la portada no válida. Asegúrate de que sigue el formato correcto."
            isValid = false
        }

        return isValid
    }

}