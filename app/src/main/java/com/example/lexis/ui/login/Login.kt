package com.example.lexis.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lexis.ui.components.CampoClave
import com.example.lexis.ui.components.LogoAnimado
import com.example.lexis.ui.components.ModalMensaje
import com.example.lexis.viewmodel.LexisViewModelFactory
import com.example.lexis.viewmodel.LoginViewModel

@Composable
fun LoginRoute(
    alAutenticar: (idUsuario: Int) -> Unit,
    alIrARegistro: () -> Unit,
    viewModel: LoginViewModel = viewModel(factory = LexisViewModelFactory.login)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.idUsuarioAutenticado) {
        uiState.idUsuarioAutenticado?.let(alAutenticar)
    }

    LoginContent(
        uiState = uiState,
        onCorreoChanged = viewModel::onCorreoChanged,
        onClaveChanged = viewModel::onClaveChanged,
        onRecordarChanged = viewModel::onRecordarChanged,
        onIngresarClick = viewModel::onIngresarClick,
        onRegistrarClick = alIrARegistro,
        onOlvidastePasswordClick = viewModel::onOlvidastePasswordClick,
        onDismissModalError = viewModel::onDismissModalError,
        onDismissModalOlvidar = viewModel::onDismissModalOlvidar
    )
}

@Composable
fun LoginContent(
    uiState: LoginUiState,
    onCorreoChanged: (String) -> Unit,
    onClaveChanged: (String) -> Unit,
    onRecordarChanged: (Boolean) -> Unit,
    onIngresarClick: () -> Unit,
    onRegistrarClick: () -> Unit,
    onOlvidastePasswordClick: () -> Unit,
    onDismissModalError: () -> Unit,
    onDismissModalOlvidar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoAnimado(tamano = 120.dp)

        Text(
            text = "LEXIS",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Tu biblioteca jurídica en el bolsillo",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = uiState.correo,
            onValueChange = onCorreoChanged,
            label = { Text("Correo") },
            singleLine = true,
            isError = uiState.errorCorreo != null,
            supportingText = uiState.errorCorreo?.let { { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        CampoClave(
            valor = uiState.clave,
            alCambiar = onClaveChanged,
            etiqueta = "Contraseña",
            mensajeError = uiState.errorClave,
            modifier = Modifier.fillMaxWidth()
        )

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
            enabled = !uiState.cargando,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.cargando) {
                CircularProgressIndicator(modifier = Modifier.height(20.dp), strokeWidth = 2.dp)
            } else {
                Text(text = "Ingresar")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onRegistrarClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Crear cuenta")
        }
    }

    if (uiState.mostrarModalError) {
        ModalMensaje(
            titulo = "No pudimos iniciar sesión",
            mensaje = uiState.mensajeError,
            alCerrar = onDismissModalError
        )
    }

    if (uiState.mostrarModalOlvidar) {
        ModalMensaje(
            titulo = "Recuperar contraseña",
            mensaje = uiState.mensajeOlvidar,
            alCerrar = onDismissModalOlvidar
        )
    }
}
