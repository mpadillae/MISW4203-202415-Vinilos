package com.mfpe.vinilos.data.model

import java.io.Serializable

data class CollectorAlbum(
    val id: Int,
    val price: Int,
    val status: String,
    val album: Album,
    val collector: Collector
) : Serializable