package com.mfpe.vinilos.data.network

import com.mfpe.vinilos.data.model.Collector
import com.mfpe.vinilos.data.model.CollectorAlbum
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CollectorService {
    @GET("collectors")
    fun getCollectors(): Call<List<Collector>>

    @GET("collectors/{collectorId}/albums")
    fun getCollectorAlbums(@Path("collectorId") collectorId: Int): Call<List<CollectorAlbum>>
}