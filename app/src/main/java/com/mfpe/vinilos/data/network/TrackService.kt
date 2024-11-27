package com.mfpe.vinilos.data.network

import com.mfpe.vinilos.data.model.Track
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.GET


interface TrackService {

    @GET("albums/{albumId}/tracks")
    fun getTracks(
        @Path("albumId") albumId: Int
    ): Call<List<Track>>

    @POST("albums/{albumId}/tracks")
    fun createTrack(
        @Path("albumId") albumId: Int,
        @Body track: Map<String, String>
    ): Call<Track>
}