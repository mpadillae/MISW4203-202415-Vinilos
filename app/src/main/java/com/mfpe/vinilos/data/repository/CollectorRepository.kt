package com.mfpe.vinilos.data.repository

import com.mfpe.vinilos.data.model.Collector
import com.mfpe.vinilos.data.model.CollectorAlbum
import com.mfpe.vinilos.data.network.CollectorService
import com.mfpe.vinilos.data.network.RetrofitApiClient
import retrofit2.Call

class CollectorRepository {
    private val collectorService: CollectorService by lazy {
        RetrofitApiClient.createRetrofitService(CollectorService::class.java)
    }

    fun getCollectors(): Call<List<Collector>> {
        return collectorService.getCollectors()
    }

    fun getCollectorAlbums(collectorId: Int): Call<List<CollectorAlbum>> {
        return collectorService.getCollectorAlbums(collectorId)
    }
}