package com.example.lexis.remote


import com.example.lexis.model.CategoriaDto
import retrofit2.http.GET
import com.example.lexis.model.NormaDto
import retrofit2.http.Query

interface LexisApiService {

    @GET("categoria")
    suspend fun obtenerCategorias(): List<CategoriaDto>
    @GET("norma")
    suspend fun obtenerNormas(
        @Query("categoria_id") categoriaId: Int
    ): List<NormaDto>
}