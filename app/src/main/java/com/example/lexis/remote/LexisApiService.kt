package com.example.lexis.remote


import com.example.lexis.model.CategoriaDto
import com.example.lexis.model.TerminoDto
import retrofit2.http.GET
import com.example.lexis.model.NormaDto
import retrofit2.http.Query
import retrofit2.http.Path
import com.example.lexis.model.ArticuloDto
import com.example.lexis.model.NovedadDto

interface LexisApiService {

    @GET("categoria")
    suspend fun obtenerCategorias(): List<CategoriaDto>
    @GET("norma")
    suspend fun obtenerNormas(
        @Query("categoria_id") categoriaId: Int
    ): List<NormaDto>
    @GET("norma/{norma_id}")
    suspend fun obtenerNorma(
        @Path("norma_id") normaId: Int
    ): NormaDto
    @GET("articulo")
    suspend fun obtenerArticulos(
        @Query("norma_id") normaId: Int
    ): List<ArticuloDto>
    @GET("novedad")
    suspend fun obtenerNovedades(): List<NovedadDto>

    @GET("glosario")
    suspend fun obtenerGlosario(): List<TerminoDto>
}