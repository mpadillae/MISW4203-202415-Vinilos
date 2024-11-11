package com.mfpe.vinilos.data.network
import com.mfpe.vinilos.data.model.Band
import retrofit2.Call
import retrofit2.http.GET

interface BandService {
    @GET("bands")
    fun getBands(): Call<List<Band>>
}