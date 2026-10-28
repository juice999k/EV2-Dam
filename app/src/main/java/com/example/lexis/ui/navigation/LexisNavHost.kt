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
import com.example.lexis.ui.registro.RegistroRoute

@Composable
fun LexisNavHost() {
    val navController = rememberNavController()
    val argumentoUsuario = listOf(navArgument(Rutas.ARGUMENTO_ID_USUARIO) { type = NavType.IntType })

    NavHost(navController = navController, startDestination = Rutas.LOGIN) {

        composable(Rutas.LOGIN) {
            LoginRoute(
                alAutenticar = { idUsuario ->
                    navController.navigate(Rutas.inicio(idUsuario)) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                },
                alIrARegistro = { navController.navigate(Rutas.REGISTRO) }
            )
        }

        composable(Rutas.REGISTRO) {
            RegistroRoute(alVolver = { navController.popBackStack() })
        }

        composable(Rutas.INICIO, arguments = argumentoUsuario) { entrada ->
            val idUsuario = entrada.arguments?.getInt(Rutas.ARGUMENTO_ID_USUARIO) ?: 0
            InicioRoute(
                idUsuario = idUsuario,
                alIrAPerfil = { },
                alIrALector = { navController.navigate(Rutas.LECTOR) }
            )
        }

        composable(Rutas.LECTOR) {
            LectorRoute(alVolver = { navController.popBackStack() })
        }
}
}
