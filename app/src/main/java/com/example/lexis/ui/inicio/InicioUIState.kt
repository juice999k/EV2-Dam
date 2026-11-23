package com.example.lexis.ui.inicio

import com.example.lexis.model.NovedadDto

data class CategoriaLegal(
    val titulo: String,
    val id: Int = 0,
    val descripcion: String = ""
)

data class InicioUiState(
    val nombreUsuario: String = "",
    val iniciales: String = "",
    val rutaFotoPerfil: String? = null,
    val saludo: String = "",
    val textoBusqueda: String = "",
    val categorias: List<CategoriaLegal> = emptyList(),
    val cargandoCategorias: Boolean = false,
    val errorCategorias: String? = null,
    val novedades: List<NovedadDto> = emptyList(),
    val cargandoNovedades: Boolean = false,
    val errorNovedades: String? = null
) {
    val categoriasFiltradas: List<CategoriaLegal>
        get() = categorias.filter {
            it.titulo.contains(textoBusqueda.trim(), ignoreCase = true)
        }
}
