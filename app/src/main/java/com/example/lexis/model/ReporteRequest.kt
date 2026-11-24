package com.example.lexis.model

import com.google.gson.annotations.SerializedName

data class ReporteRequest(
    @SerializedName("norma_id")
    val normaId: Int,
    val descripcion: String,
    @SerializedName("articulo_id")
    val articuloId: Int? = null
)