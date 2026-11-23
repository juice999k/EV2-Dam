package com.example.lexis.model

import com.google.gson.annotations.SerializedName

data class ArticuloDto(
    val id: Int,
    @SerializedName("norma_id")
    val normaId: Int,
    val numero: String,
    val titulo: String,
    val contenido: String
)