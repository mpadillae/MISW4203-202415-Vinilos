package com.mfpe.vinilos.data.repository

import com.mfpe.vinilos.data.model.Album
import com.mfpe.vinilos.data.network.AlbumService
import com.mfpe.vinilos.data.network.RetrofitApiClient
import retrofit2.Call

class AlbumRepository {

    private val albumService: AlbumService by lazy {
        RetrofitApiClient.createRetrofitService(AlbumService::class.java)
    }

    fun getAlbums(): Call<List<Album>> {
        return albumService.getAlbums()
    }

    fun addAlbum(album: Album): Call<Album> {
        return albumService.addAlbum(album)
    }


}