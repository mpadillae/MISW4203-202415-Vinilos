package com.mfpe.vinilos.models

import java.io.Serializable
import java.util.Date

data class Performer (
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: Date
) : Serializable