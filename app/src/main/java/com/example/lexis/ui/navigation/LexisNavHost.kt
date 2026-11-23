package com.example.lexis.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lexis.ui.inicio.InicioRoute
import com.example.lexis.ui.lector.LectorRoute
import com.example.lexis.ui.login.LoginRoute
import com.example.lexis.ui.normas.DetalleNormaRoute
import com.example.lexis.ui.normas.NormasRoute
import com.example.lexis.ui.perfil.PerfilRoute
import com.example.lexis.ui.registro.RegistroRoute

@Composable
fun LexisNavHost() {
    val navController = rememberNavController()

    val argumentoUsuario = listOf(
        navArgument(Rutas.ARGUMENTO_ID_USUARIO) {
            type = NavType.IntType
        }
    )

    NavHost(
        navController = navController,
        startDestination = Rutas.LOGIN
    ) {
        composable(Rutas.LOGIN) {
            LoginRoute(
                alAutenticar = { idUsuario ->
                    navController.navigate(Rutas.inicio(idUsuario)) {
                        popUpTo(Rutas.LOGIN) {
                            inclusive = true
                        }
                    }
                },
                alIrARegistro = {
                    navController.navigate(Rutas.REGISTRO)
                }
            )
        }

        composable(Rutas.REGISTRO) {
            RegistroRoute(
                alVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Rutas.INICIO,
            arguments = argumentoUsuario
        ) { entrada ->
            val idUsuario = requireNotNull(entrada.arguments)
                .getInt(Rutas.ARGUMENTO_ID_USUARIO)

            InicioRoute(
                idUsuario = idUsuario,
                alIrAPerfil = {
                    navController.navigate(Rutas.perfil(idUsuario))
                },
                alIrANormas = { categoriaId ->
                    navController.navigate(Rutas.normas(categoriaId))
                }
            )
        }

        composable(
            route = Rutas.NORMAS,
            arguments = listOf(
                navArgument(Rutas.ARGUMENTO_ID_CATEGORIA) {
                    type = NavType.IntType
                }
            )
        ) { entrada ->
            val categoriaId = requireNotNull(entrada.arguments)
                .getInt(Rutas.ARGUMENTO_ID_CATEGORIA)

            NormasRoute(
                categoriaId = categoriaId,
                alVolver = {
                    navController.popBackStack()
                },
                alIrADetalle = { normaId ->
                    navController.navigate(Rutas.detalleNorma(normaId))
                }
            )
        }

        composable(
            route = Rutas.DETALLE_NORMA,
            arguments = listOf(
                navArgument(Rutas.ARGUMENTO_ID_NORMA) {
                    type = NavType.IntType
                }
            )
        ) { entrada ->
            val normaId = requireNotNull(entrada.arguments)
                .getInt(Rutas.ARGUMENTO_ID_NORMA)

            DetalleNormaRoute(
                normaId = normaId,
                alVolver = {
                    navController.popBackStack()
                },
                alLeerArticulos = { idNorma ->
                    navController.navigate(Rutas.lector(idNorma))
                }
            )
        }

        composable(
            route = Rutas.LECTOR,
            arguments = listOf(
                navArgument(Rutas.ARGUMENTO_ID_NORMA) {
                    type = NavType.IntType
                }
            )
        ) { entrada ->
            val normaId = requireNotNull(entrada.arguments)
                .getInt(Rutas.ARGUMENTO_ID_NORMA)

            LectorRoute(
                normaId = normaId,
                alVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Rutas.PERFIL,
            arguments = argumentoUsuario
        ) { entrada ->
            val idUsuario = requireNotNull(entrada.arguments)
                .getInt(Rutas.ARGUMENTO_ID_USUARIO)

            PerfilRoute(
                idUsuario = idUsuario,
                alVolver = {
                    navController.popBackStack()
                },
                alCerrarSesion = {
                    navController.navigate(Rutas.LOGIN) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
