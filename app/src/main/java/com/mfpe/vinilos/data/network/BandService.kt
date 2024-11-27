package com.mfpe.vinilos.data.network
import com.mfpe.vinilos.data.model.Band
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BandService {
    @GET("bands")
    fun getBands(): Call<List<Band>>

    @GET("bands/{id}")
    fun getBandById(@Path("id") id: Int): Call<Band>
}