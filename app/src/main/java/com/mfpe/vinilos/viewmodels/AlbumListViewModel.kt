package com.mfpe.vinilos.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mfpe.vinilos.models.Album
import com.mfpe.vinilos.network.AlbumService
import com.mfpe.vinilos.network.RetrofitApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumListViewModel : ViewModel() {

    private val albumService = RetrofitApiClient.createRetrofitService(AlbumService::class.java)

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> get() = _albums

    private var _networkError = MutableLiveData<Boolean>(false)
    val networkError: LiveData<Boolean> get() = _networkError

    fun fetchAlbums() {
        albumService.getAlbums().enqueue(object : Callback<List<Album>> {
            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                if (response.isSuccessful) {
                    _albums.value = response.body()
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