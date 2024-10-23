package com.mfpe.vinilos.models

import java.io.Serializable

data class Track (
    val id: Int,
    val name: String,
    val duration: String
) : Serializable