package com.mfpe.vinilos.network

import com.mfpe.vinilos.models.Album
import retrofit2.Call
import retrofit2.http.GET

interface AlbumService {

    @GET("albums")
    fun getAlbums(): Call<List<Album>>

}