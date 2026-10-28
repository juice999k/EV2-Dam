package com.example.lexis.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lexis.ui.inicio.InicioScreen
import com.example.lexis.ui.login.LoginRoute
import com.example.lexis.ui.registro.RegistroRoute

@Composable
fun LexisNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.LOGIN
    ) {
        composable(Rutas.LOGIN) {
            LoginRoute(
                alAutenticar = { idUsuario ->
                    navController.navigate(Rutas.inicio(idUsuario)) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                },
                alIrARegistro = {
                    navController.navigate(Rutas.REGISTRO)
                }
            )
        }

        composable(Rutas.REGISTRO) {
            RegistroRoute(
                alVolver = { navController.popBackStack() }
            )
        }

        composable(
            route = Rutas.INICIO,
            arguments = listOf(
                navArgument(Rutas.ARGUMENTO_ID_USUARIO) {
                    type = NavType.IntType
                }
            )
        ) {
            InicioScreen()
        }
    }
}