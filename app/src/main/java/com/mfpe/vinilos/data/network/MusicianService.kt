package com.mfpe.vinilos.data.network

import com.mfpe.vinilos.data.model.Musician
import retrofit2.Call
import retrofit2.http.GET

interface MusicianService {

    @GET("musicians")
    fun getMusician(): Call<List<Musician>>

}