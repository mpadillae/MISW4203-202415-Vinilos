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

class AlbumListViewModel : ViewModel() {

    private val albumRepository: AlbumRepository = AlbumRepository()

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> get() = _albums

    private var _networkError = MutableLiveData(false)
    val networkError: LiveData<Boolean> get() = _networkError

    fun fetchAlbums() {
        albumRepository.getAlbums().enqueue(object : Callback<List<Album>> {
            override fun onResponse(call: Call<List<Album>>, res: Response<List<Album>>) {
                if (res.isSuccessful) {
                    _albums.value = res.body()
                    _networkError.value = false
                }
            }

            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                _networkError.value = true
                Log.d("Error", t.toString())
            }
        })
    }

}