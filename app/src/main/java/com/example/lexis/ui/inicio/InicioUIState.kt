package com.example.lexis.ui.inicio

import com.example.lexis.model.LeyDemo

data class CategoriaLegal(
    val titulo: String,
    val id: Int = 0,
    val descripcion: String = ""
)
data class CambioReciente(
    val dia: String,
    val mes: String,
    val etiqueta: String,
    val titulo: String,
    val subtitulo: String,
    val tiempoYCantidad: String
)

data class InicioUiState(
    val nombreUsuario: String = "",
    val iniciales: String = "",
    val rutaFotoPerfil: String? = null,
    val saludo: String = "",
    val textoBusqueda: String = "",
    val tituloUltimaLectura: String = LeyDemo.TITULO,
    val articuloUltimaLectura: String = "Artículo 5 · Derechos del titular",
    val progresoLectura: Float = 0.68f,
    val categorias: List<CategoriaLegal> = emptyList(),
    val cargandoCategorias: Boolean = false,
    val errorCategorias: String? = null,
    val cambiosRecientes: List<CambioReciente> = listOf(
        CambioReciente(
            dia = "14",
            mes = "MAY",
            etiqueta = "MODIFICADA",
            titulo = "Ley Federal del Trabajo",
            subtitulo = "Reforma sobre jornadas y descanso laboral",
            tiempoYCantidad = "Hace 2 días · 4 artículos"
        )
    )
) {
    val categoriasFiltradas: List<CategoriaLegal>
        get() = categorias.filter { it.titulo.contains(textoBusqueda.trim(), ignoreCase = true) }
}
