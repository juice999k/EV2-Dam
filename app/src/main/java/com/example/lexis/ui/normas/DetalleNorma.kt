package com.example.lexis.ui.normas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lexis.viewmodel.DetalleNormaViewModel
import com.example.lexis.viewmodel.LexisViewModelFactory

@Composable
fun DetalleNormaRoute(
    normaId: Int,
    alVolver: () -> Unit,
    alLeerArticulos: (Int) -> Unit,
    viewModel: DetalleNormaViewModel = viewModel(
        factory = LexisViewModelFactory.detalleNorma(normaId)
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetalleNormaContent(
        uiState = uiState,
        onVolver = alVolver,
        onReintentar = viewModel::cargarNorma,
        onLeerArticulos = alLeerArticulos,
        onAbrirReporte = viewModel::abrirFormularioReporte,
        onCerrarReporte = viewModel::cerrarFormularioReporte,
        onDescripcionReporteChanged = viewModel::onDescripcionReporteChanged,
        onEnviarReporte = viewModel::enviarReporte,
        onCerrarConfirmacion = viewModel::cerrarConfirmacionReporte
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleNormaContent(
    uiState: DetalleNormaUiState,
    onVolver: () -> Unit,
    onReintentar: () -> Unit,
    onLeerArticulos: (Int) -> Unit,
    onAbrirReporte: () -> Unit,
    onCerrarReporte: () -> Unit,
    onDescripcionReporteChanged: (String) -> Unit,
    onEnviarReporte: () -> Unit,
    onCerrarConfirmacion: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Detalle de la norma")
                },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val error = uiState.error
            val norma = uiState.norma

            when {
                uiState.cargando -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CircularProgressIndicator()
                        Text("Cargando detalle...")
                    }
                }

                error != null -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )

                        Button(onClick = onReintentar) {
                            Text("Reintentar")
                        }
                    }
                }

                norma != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(24.dp)
                    ) {
                        Text(
                            text = norma.titulo,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = norma.resumen,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                onLeerArticulos(norma.id)
                            }
                        ) {
                            Text("Leer artículos")
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedButton(onClick = onAbrirReporte) {
                            Text("Reportar un problema")
                        }

                        if (norma.esDemo) {
                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "Contenido educativo: incluye resúmenes, no el texto oficial íntegro.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                else -> {
                    Text(
                        text = "No hay información disponible.",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp)
                    )
                }
            }
        }
    }

    if (uiState.mostrarFormularioReporte) {
        AlertDialog(
            onDismissRequest = onCerrarReporte,
            title = { Text("Reportar un problema") },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Describe el problema de esta norma. No incluyas datos personales.")

                    OutlinedTextField(
                        value = uiState.descripcionReporte,
                        onValueChange = onDescripcionReporteChanged,
                        label = { Text("Descripción") },
                        enabled = !uiState.enviandoReporte,
                        minLines = 3,
                        maxLines = 6,
                        isError = uiState.errorDescripcionReporte != null,
                        supportingText = {
                            Text(
                                uiState.errorDescripcionReporte
                                    ?: "Entre 10 y 1000 caracteres."
                            )
                        }
                    )

                    uiState.errorEnvioReporte?.let { mensaje ->
                        Text(mensaje, color = MaterialTheme.colorScheme.error)
                    }

                    if (uiState.enviandoReporte) {
                        Text("Enviando reporte...")
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = onEnviarReporte,
                    enabled = !uiState.enviandoReporte
                ) {
                    Text("Enviar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onCerrarReporte,
                    enabled = !uiState.enviandoReporte
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (uiState.mostrarConfirmacionReporte) {
        AlertDialog(
            onDismissRequest = onCerrarConfirmacion,
            title = { Text("Reporte enviado") },
            text = { Text("El reporte se guardó correctamente. Gracias por tu ayuda.") },
            confirmButton = {
                TextButton(onClick = onCerrarConfirmacion) {
                    Text("Aceptar")
                }
            }
        )
    }
}