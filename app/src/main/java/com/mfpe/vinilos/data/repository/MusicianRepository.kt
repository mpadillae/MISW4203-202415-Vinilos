package com.mfpe.vinilos.data.repository

import com.mfpe.vinilos.data.model.Musician
import com.mfpe.vinilos.data.network.MusicianService
import com.mfpe.vinilos.data.network.RetrofitApiClient
import retrofit2.Call


class MusicianRepository {
    private val musicianRepository: MusicianService by lazy {
        RetrofitApiClient.createRetrofitService(MusicianService::class.java)
    }

    fun getMusician(): Call<List<Musician>>{
        return musicianRepository.getMusician()
    }

    fun getMusicianById(id: Int): Call<Musician> {
        return musicianRepository.getMusicianById(id)
    }

}
