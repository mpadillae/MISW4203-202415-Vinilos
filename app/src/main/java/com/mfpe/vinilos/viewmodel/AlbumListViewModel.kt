package com.mfpe.vinilos.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfpe.vinilos.data.model.Album
import com.mfpe.vinilos.data.model.requests.CreateAlbumRequest
import com.mfpe.vinilos.data.repository.AlbumRepository
import kotlinx.coroutines.launch
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

    private val _selectedAlbum = MutableLiveData<Album>()
    val selectedAlbum: LiveData<Album> get() = _selectedAlbum

    fun addAlbum(createAlbumRequest: CreateAlbumRequest, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            albumRepository.addAlbum(createAlbumRequest).enqueue(object : Callback<Album> {
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
    }

    fun fetchAlbums() {
        viewModelScope.launch {
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

    fun fetchAlbumById(id:Int){
        viewModelScope.launch {
            albumRepository.getAlbumById(id).enqueue(object : Callback<Album>{

                override fun onResponse(call: Call<Album>, res: Response<Album>) {
                    if (res.isSuccessful) {
                        _selectedAlbum.value = res.body()
                        _networkError.value = false
                    }
                }

                override fun onFailure(call: Call<Album>, t: Throwable) {
                    _networkError.value = true
                    Log.d("Error", t.toString())
                }            })


        }
    }



}