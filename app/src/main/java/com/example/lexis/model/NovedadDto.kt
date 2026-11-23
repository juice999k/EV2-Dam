package com.example.lexis.model

import com.google.gson.annotations.SerializedName

data class NovedadDto(
    val id: Int,
    @SerializedName("norma_id")
    val normaId: Int,
    val titulo: String,
    val descripcion: String,
    val fecha: String
)