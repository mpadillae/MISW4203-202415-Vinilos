package com.mfpe.vinilos.data.model

import java.io.Serializable
import java.util.Date

data class Performer (
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: Date,
    val creationDate: Date?
) : Serializable