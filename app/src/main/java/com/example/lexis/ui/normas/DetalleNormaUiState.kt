package com.example.lexis.ui.normas

import com.example.lexis.model.NormaDto

data class DetalleNormaUiState(
    val norma: NormaDto? = null,
    val cargando: Boolean = false,
    val error: String? = null,
    val mostrarFormularioReporte: Boolean = false,
    val descripcionReporte: String = "",
    val errorDescripcionReporte: String? = null,
    val enviandoReporte: Boolean = false,
    val errorEnvioReporte: String? = null,
    val mostrarConfirmacionReporte: Boolean = false
)
