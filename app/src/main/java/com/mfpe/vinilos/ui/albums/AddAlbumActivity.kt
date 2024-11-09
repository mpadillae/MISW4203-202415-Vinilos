package com.mfpe.vinilos.ui.albums

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.mfpe.vinilos.R
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mfpe.vinilos.viewmodel.AlbumListViewModel
import android.graphics.Color
import com.mfpe.vinilos.data.model.Album
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddAlbumActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etUrlCover: EditText
    private lateinit var spGenre: Spinner
    private lateinit var etReleaseDate: EditText
    private lateinit var spRecordLabel: Spinner
    private lateinit var etDescription: EditText
    private lateinit var btnAddAlbum: Button
    private lateinit var albumListViewModel: AlbumListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add_album)

        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        albumListViewModel = ViewModelProvider(this)[AlbumListViewModel::class.java]

        etName = findViewById(R.id.et_name)
        etUrlCover = findViewById(R.id.et_url_cover)
        spGenre = findViewById(R.id.sp_genre)
        etReleaseDate = findViewById(R.id.et_release_date)
        spRecordLabel = findViewById(R.id.sp_record_label)
        etDescription = findViewById(R.id.et_description)
        btnAddAlbum = findViewById(R.id.btn_add_album)

        setupSpinners()

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
                            Toast.makeText(this, "Álbum agregado exitosamente", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Error al agregar el álbum", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Fecha inválida", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isValidUrlCover(url: String): Boolean {
        val regex = "^https://cdns-images\\.dzcdn\\.net/images/cover/[a-f0-9]{32}/[0-9x]+-[0-9]{6}-80-0-0\\.jpg$".toRegex()
        return url.matches(regex)
    }

    private fun setupSpinners() {
        val genres = listOf("Classical", "Salsa", "Rock", "Folk")
        val recordLabels = listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")

        val genreAdapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genres) {
            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val view = super.getView(position, convertView, parent)
                (view as TextView).apply {
                    text = if (spGenre.selectedItemPosition == -1) "Género" else getItem(position)
                    setTextColor(
                        if (spGenre.selectedItemPosition == -1)
                            Color.parseColor("#505050")
                        else Color.BLACK
                    )
                }
                return view
            }
        }

        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spGenre.adapter = genreAdapter
        spGenre.setSelection(-1)

        val recordLabelAdapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, recordLabels) {
            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val view = super.getView(position, convertView, parent)
                (view as TextView).apply {
                    text = if (spRecordLabel.selectedItemPosition == -1) "Disquera" else getItem(position)
                    setTextColor(
                        if (spRecordLabel.selectedItemPosition == -1)
                            Color.parseColor("#505050")
                        else Color.BLACK
                    )
                }
                return view
            }
        }
        recordLabelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spRecordLabel.adapter = recordLabelAdapter
        spRecordLabel.setSelection(-1)
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


        if (spGenre.selectedItem == "Género") {
            Toast.makeText(this, "Por favor, selecciona un género", Toast.LENGTH_SHORT).show()
            isValid = false
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