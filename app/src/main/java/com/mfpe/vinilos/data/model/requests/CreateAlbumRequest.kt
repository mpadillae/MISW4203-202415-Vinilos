package com.mfpe.vinilos.data.model.requests

import java.io.Serializable
import java.util.Date

data class CreateAlbumRequest (
    val name: String,
    val cover: String,
    val releaseDate: Date,
    val description: String,
    val genre: String,
    val recordLabel: String
) : Serializable