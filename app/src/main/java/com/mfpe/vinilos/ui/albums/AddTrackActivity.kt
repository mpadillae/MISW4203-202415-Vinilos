package com.mfpe.vinilos.ui.albums

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mfpe.vinilos.R
import com.mfpe.vinilos.data.model.Track
import com.mfpe.vinilos.ui.shared.MainActivity
import com.mfpe.vinilos.data.network.RetrofitApiClient
import com.mfpe.vinilos.data.network.TrackService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent


class AddTrackActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var saveButton: Button
    private lateinit var etName: EditText
    private lateinit var etDuration: EditText

    private var albumId: Int = -1

    private val tag = "AddTrackActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_track)

        albumId = intent.getIntExtra("albumId", -1)

        if (albumId == -1) {
            Toast.makeText(this, "Error al cargar el álbum.", Toast.LENGTH_SHORT).show()
            finish()

            return
        }

        backButton = findViewById(R.id.back_button)
        saveButton = findViewById(R.id.btn_add_track)
        etName = findViewById(R.id.et_name)

        etDuration = findViewById(R.id.et_duration)

        backButton.setOnClickListener {
            Log.d(tag, "Botón de retroceso presionado.")
            finish()
        }
        saveButton.setOnClickListener {
            Log.d(tag, "Botón de guardar presionado.")
            validateAndSaveTrack()
        }
    }

    private fun validateAndSaveTrack() {
        val name = etName.text.toString().trim()
        val duration = etDuration.text.toString().trim()


        if (name.isEmpty() || name.length > 200) {
            Toast.makeText(this, "El nombre debe tener entre 1 y 200 caracteres.", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidDuration(duration)) {
            Toast.makeText(this, "Formato de duración inválido. Debe ser mm:ss.", Toast.LENGTH_SHORT).show()
            return
        }

        val track = Track(id=0,name = name, duration = duration)
        saveTrack(track)
    }

    private fun saveTrack(track: Track) {
        if (albumId == -1) {
            Log.e(tag, "No se puede guardar el track: Album ID no válido")
            return
        }

        val trackData = mapOf(
            "name" to track.name,
            "duration" to track.duration
        )

        val trackService = RetrofitApiClient.createRetrofitService(TrackService::class.java)
        trackService.createTrack(albumId, trackData).enqueue(object : Callback<Track> {
            override fun onResponse(call: Call<Track>, response: Response<Track>) {

                if (response.isSuccessful) {
                    Log.d(tag, "Track creado exitosamente: ${response.body()}")
                    Toast.makeText(this@AddTrackActivity, "Track creado exitosamente.", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@AddTrackActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Sin detalles"
                    Log.e(tag, "Error al crear el track. Código: ${response.code()}, Mensaje: ${response.message()}, Detalles: $errorBody")
                    Toast.makeText(this@AddTrackActivity, "Error al crear el track: $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Track>, t: Throwable) {
                Log.e(tag, "Error de red al crear el track: ${t.message}")
                Toast.makeText(this@AddTrackActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun isValidDuration(duration: String): Boolean {
        val regex = "^([0-5]?[0-9]):([0-5]?[0-9])$".toRegex()
        val isValid = regex.matches(duration)
        Log.d(tag, "Validación de duración: '$duration', válido: $isValid")
        return isValid
    }
}