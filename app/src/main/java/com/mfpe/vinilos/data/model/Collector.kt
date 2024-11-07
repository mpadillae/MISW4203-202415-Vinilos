package com.mfpe.vinilos.data.model

import java.io.Serializable
import java.util.Date

data class Collector(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<Comment>,
    val favoritePerformers: List<Performer>,
    val collectorAlbums: List<CollectorAlbum>
) : Serializable
