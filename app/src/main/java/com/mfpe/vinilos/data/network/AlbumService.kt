package com.mfpe.vinilos.data.network

import com.mfpe.vinilos.data.model.Album
import retrofit2.Call
import retrofit2.http.GET

interface AlbumService {

    @GET("albums")
    fun getAlbums(): Call<List<Album>>

}