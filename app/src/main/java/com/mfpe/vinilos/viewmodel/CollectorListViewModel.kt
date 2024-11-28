package com.mfpe.vinilos.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfpe.vinilos.data.model.Collector
import com.mfpe.vinilos.data.model.CollectorAlbum
import com.mfpe.vinilos.data.repository.CollectorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CollectorListViewModel : ViewModel() {

    private val collectorRepository: CollectorRepository = CollectorRepository()

    private val _collectors = MutableLiveData<List<Collector>>()
    val collectors: LiveData<List<Collector>> get() = _collectors

    private val _collectorAlbums = MutableLiveData<List<CollectorAlbum>>()
    val collectorAlbums: LiveData<List<CollectorAlbum>> get() = _collectorAlbums

    private var _networkError = MutableLiveData(false)
    val networkError: LiveData<Boolean> get() = _networkError

    fun fetchCollectors() {
        viewModelScope.launch(Dispatchers.IO) {
            collectorRepository.getCollectors().enqueue(object : Callback<List<Collector>> {
                override fun onResponse(call: Call<List<Collector>>, res: Response<List<Collector>>) {
                    if (res.isSuccessful) {
                        _collectors.value = res.body()?.sortedBy { it.name }
                        _networkError.value = false
                    }
                }

                override fun onFailure(call: Call<List<Collector>>, t: Throwable) {
                    _networkError.value = true
                    Log.d("Error", t.toString())
                }
            })
        }
    }

    fun fetchCollectorAlbums(collectorId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            collectorRepository.getCollectorAlbums(collectorId).enqueue(object : Callback<List<CollectorAlbum>> {
                override fun onResponse(call: Call<List<CollectorAlbum>>, res: Response<List<CollectorAlbum>>) {
                    if (res.isSuccessful) {
                        _collectorAlbums.value = res.body()?.sortedBy { it.album.name }
                        _networkError.value = false
                    }
                }

                override fun onFailure(call: Call<List<CollectorAlbum>>, t: Throwable) {
                    _networkError.value = true
                    Log.d("Error", t.toString())
                }
            })
        }
    }
}