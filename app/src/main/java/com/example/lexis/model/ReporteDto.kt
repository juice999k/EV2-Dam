package com.example.lexis.model

import com.google.gson.annotations.SerializedName

data class ReporteDto(
    val id: Int,
    @SerializedName("norma_id")
    val normaId: Int,
    @SerializedName("articulo_id")
    val articuloId: Int?,
    val descripcion: String
)