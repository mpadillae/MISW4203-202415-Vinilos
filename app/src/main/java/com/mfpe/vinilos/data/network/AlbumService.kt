package com.mfpe.vinilos.data.network

import com.mfpe.vinilos.data.model.Album
import com.mfpe.vinilos.data.model.requests.CreateAlbumRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body


interface AlbumService {

    @GET("albums")
    fun getAlbums(): Call<List<Album>>

    @POST("albums")
    fun addAlbum(@Body createAlbumRequest: CreateAlbumRequest): Call<Album>
}