package com.mfpe.vinilos.data.model
import java.io.Serializable


data class Band (
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val musicians : List<Musician>
    ): Serializable