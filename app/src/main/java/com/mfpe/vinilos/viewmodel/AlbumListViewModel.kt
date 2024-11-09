package com.mfpe.vinilos.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mfpe.vinilos.data.model.Album
import com.mfpe.vinilos.data.repository.AlbumRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AlbumListViewModel : ViewModel() {

    private val albumRepository: AlbumRepository = AlbumRepository()

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> get() = _albums

    private var _networkError = MutableLiveData(false)
    val networkError: LiveData<Boolean> get() = _networkError

    fun addAlbum(album: Album, onResult: (Boolean) -> Unit) {
        albumRepository.addAlbum(album).enqueue(object : Callback<Album> {
            override fun onResponse(call: Call<Album>, response: Response<Album>) {
                if (response.isSuccessful) {
                    onResult(true)
                } else {
                    try {
                        val errorBody = response.errorBody()?.string()
                        Log.e("Error", "Error al agregar el álbum: ${response.code()} - $errorBody")
                    } catch (e: IOException) {
                        Log.e("Error", "Error al leer el cuerpo del error: ${e.message}")
                    }
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<Album>, t: Throwable) {
                Log.e("Error", "Failed to add album: ${t.message}")
                onResult(false)
            }
        })
    }

    fun fetchAlbums() {
        albumRepository.getAlbums().enqueue(object : Callback<List<Album>> {
            override fun onResponse(call: Call<List<Album>>, res: Response<List<Album>>) {
                if (res.isSuccessful) {
                    _albums.value = res.body()
                    _networkError.value = false
                } else {
                    try {
                        val errorBody = res.errorBody()?.string()
                        Log.e("Error", "Failed to fetch albums: ${res.code()} - $errorBody")
                    } catch (e: IOException) {
                        Log.e("Error", "Error al leer el cuerpo del error: ${e.message}")
                    }
                    _networkError.value = true
                }
            }

            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                _networkError.value = true
                Log.e("Error", "Failed to fetch albums: ${t.message}")
            }
        })
    }
}