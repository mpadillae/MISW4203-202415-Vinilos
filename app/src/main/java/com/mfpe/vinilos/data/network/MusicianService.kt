package com.mfpe.vinilos.data.network

import com.mfpe.vinilos.data.model.Musician
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicianService {

    @GET("musicians")
    fun getMusician(): Call<List<Musician>>

    @GET("musicians/{id}")
    fun getMusicianById(@Path("id") id: Int): Call<Musician>

}