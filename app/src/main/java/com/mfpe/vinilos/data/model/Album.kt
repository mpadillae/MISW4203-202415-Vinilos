package com.mfpe.vinilos.data.model

import java.io.Serializable
import java.util.Date

data class Album(
    val id: Int? = null,
    val name: String,
    val cover: String,
    val releaseDate: Date,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val tracks: List<Track>? = null,
    val performers: List<Performer>? = null,
    val comments: List<Comment>? = null
) : Serializable