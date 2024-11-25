package com.mfpe.vinilos.data.model
import java.io.Serializable
import java.util.Date


data class Musician (
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: Date,
    val band : Band,
    var albums: List<Album>

): Serializable