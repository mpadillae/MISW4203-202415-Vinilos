package com.mfpe.vinilos.models

import java.io.Serializable
import java.util.Date

data class Album (
    val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: Date,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val tracks: List<Track>,
    val performers: List<Performer>,
    val comments: List<Comment>
) : Serializable