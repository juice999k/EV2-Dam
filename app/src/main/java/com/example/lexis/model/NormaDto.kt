package com.example.lexis.model

import com.google.gson.annotations.SerializedName

data class NormaDto(
    val id: Int,
    @SerializedName("categoria_id")
    val categoriaId: Int,
    val titulo: String,
    val resumen: String,
    @SerializedName("es_demo")
    val esDemo: Boolean
)