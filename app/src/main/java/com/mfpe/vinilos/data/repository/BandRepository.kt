package com.mfpe.vinilos.data.repository

import com.mfpe.vinilos.data.model.Band
import com.mfpe.vinilos.data.network.BandService
import com.mfpe.vinilos.data.network.RetrofitApiClient
import retrofit2.Call

class BandRepository {

    private val bandService: BandService by lazy{
        RetrofitApiClient.createRetrofitService(BandService::class.java)
    }

    fun getBands(): Call<List<Band>>{
        return bandService.getBands()
    }
}