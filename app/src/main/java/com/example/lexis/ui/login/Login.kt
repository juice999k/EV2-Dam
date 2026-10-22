package com.example.lexis.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lexis.ui.viewmodel.LoginViewModel

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginContent(
        uiState = uiState,
        onUsernameChanged = viewModel::onUsernameChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onRecordarChanged = viewModel::onRecordarChanged,
        onIngresarClick = viewModel::onIngresarClick,
        onDismissModalLogin = viewModel::onDismissModalLogin,
        onRegistrarClick = viewModel::onRegistrarClick,
        onDismissModalRegistro = viewModel::onDismissModalRegistro,
        onOlvidastePasswordClick = viewModel::onOlvidastePasswordClick,
        onDismissModalOlvidar = viewModel::onDismissModalOlvidar
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    uiState: LoginUiState,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRecordarChanged: (Boolean) -> Unit,
    onIngresarClick: () -> Unit,
    onDismissModalLogin: () -> Unit,
    onRegistrarClick: () -> Unit,
    onDismissModalRegistro: () -> Unit,
    onOlvidastePasswordClick: () -> Unit,
    onDismissModalOlvidar: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lexis - App Jurídica") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Correo")

            OutlinedTextField(
                value = uiState.username,
                onValueChange = onUsernameChanged,
                label = { Text("Introduce tu correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Contraseña")

            OutlinedTextField(
                value = uiState.password,
                onValueChange = onPasswordChanged,
                label = { Text("Introduce tu contraseña") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = uiState.recordarUsuario,
                        onCheckedChange = onRecordarChanged
                    )
                    Text(text = "Recordar")
                }

                TextButton(onClick = onOlvidastePasswordClick) {
                    Text(text = "¿Olvidaste tu contraseña?")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onIngresarClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Ingresar")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onRegistrarClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Registrarse")
            }

            if (uiState.mostrarModalLogin) {
                AlertDialog(
                    onDismissRequest = onDismissModalLogin,
                    title = { Text("Mensaje") },
                    text = { Text("Iniciando sesión con: ${uiState.username}") },
                    confirmButton = {
                        Button(onClick = onDismissModalLogin) {
                            Text("OK")
                        }
                    }
                )
            }

            if (uiState.mostrarModalRegistro) {
                AlertDialog(
                    onDismissRequest = onDismissModalRegistro,
                    title = { Text("Registro de Usuario") },
                    text = { Text(uiState.mensajeRegistro) },
                    confirmButton = {
                        Button(onClick = onDismissModalRegistro) {
                            Text("Aceptar")
                        }
                    }
                )
            }

            if (uiState.mostrarModalOlvidar) {
                AlertDialog(
                    onDismissRequest = onDismissModalOlvidar,
                    title = { Text("Recuperar Contraseña") },
                    text = { Text(uiState.mensajeOlvidar) },
                    confirmButton = {
                        Button(onClick = onDismissModalOlvidar) {
                            Text("Aceptar")
                        }
                    }
                )
            }
        }
    }
}