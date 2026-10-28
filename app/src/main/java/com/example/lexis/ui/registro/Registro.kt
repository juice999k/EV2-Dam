package com.example.lexis.ui.registro

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lexis.model.TipoUsuario
import com.example.lexis.ui.components.CampoClave
import com.example.lexis.ui.components.ModalMensaje
import com.example.lexis.viewmodel.LexisViewModelFactory
import com.example.lexis.viewmodel.RegistroViewModel

@Composable
fun RegistroRoute(
    alVolver: () -> Unit,
    viewModel: RegistroViewModel = viewModel(factory = LexisViewModelFactory.registro)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState.registroCompletado) {
        if (uiState.registroCompletado) alVolver()
    }

    RegistroContent(
        uiState = uiState,
        onNombreChanged = viewModel::onNombreChanged,
        onCorreoChanged = viewModel::onCorreoChanged,
        onClaveChanged = viewModel::onClaveChanged,
        onConfirmacionChanged = viewModel::onConfirmacionChanged,
        onTipoUsuarioChanged = viewModel::onTipoUsuarioChanged,
        onRegistrarClick = viewModel::onRegistrarClick,
        onModalExitoCerrado = viewModel::onModalExitoCerrado,
        onVolverClick = alVolver
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroContent(
    uiState: RegistroUiState,
    onNombreChanged: (String) -> Unit,
    onCorreoChanged: (String) -> Unit,
    onClaveChanged: (String) -> Unit,
    onConfirmacionChanged: (String) -> Unit,
    onTipoUsuarioChanged: (TipoUsuario) -> Unit,
    onRegistrarClick: () -> Unit,
    onModalExitoCerrado: () -> Unit,
    onVolverClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear cuenta") },
                navigationIcon = {
                    IconButton(onClick = onVolverClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = onNombreChanged,
                label = { Text("Nombre completo") },
                singleLine = true,
                isError = uiState.errorNombre != null,
                supportingText = uiState.errorNombre?.let { { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

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

            CampoClave(
                valor = uiState.clave,
                alCambiar = onClaveChanged,
                etiqueta = "Contraseña",
                mensajeError = uiState.errorClave,
                modifier = Modifier.fillMaxWidth()
            )

            CampoClave(
                valor = uiState.confirmacionClave,
                alCambiar = onConfirmacionChanged,
                etiqueta = "Repetir contraseña",
                mensajeError = uiState.errorConfirmacion,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Tipo de usuario",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TipoUsuario.entries.forEach { tipo ->
                    FilterChip(
                        selected = uiState.tipoUsuario == tipo,
                        onClick = { onTipoUsuarioChanged(tipo) },
                        label = { Text(tipo.etiqueta) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onRegistrarClick,
                enabled = !uiState.cargando,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.cargando) {
                    CircularProgressIndicator(modifier = Modifier.height(20.dp), strokeWidth = 2.dp)
                } else {
                    Text("Registrarme")
                }
            }
        }
    }

    if (uiState.mostrarModalExito) {
        ModalMensaje(
            titulo = "¡Cuenta creada!",
            mensaje = "Ya puedes iniciar sesión con tu correo y contraseña.",
            alCerrar = onModalExitoCerrado
        )
    }
}
