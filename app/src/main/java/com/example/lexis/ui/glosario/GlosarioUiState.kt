package com.example.lexis.ui.glosario

import com.example.lexis.model.TerminoDto

data class GlosarioUiState(
    val terminos: List<TerminoDto> = emptyList(),
    val busqueda: String = "",
    val cargando: Boolean = false,
    val error: String? = null
) {
    val terminosFiltrados: List<TerminoDto>
        get() = terminos.filter {
            it.termino.contains(busqueda.trim(), ignoreCase = true)
        }
}
