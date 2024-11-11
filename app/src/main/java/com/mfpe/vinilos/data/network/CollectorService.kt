package com.mfpe.vinilos.data.network

import com.mfpe.vinilos.data.model.Collector
import retrofit2.Call
import retrofit2.http.GET

interface CollectorService {
    @GET("collectors")
    fun getCollectors(): Call<List<Collector>>
}