package com.example.lexis.ui.glosario

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.lexis.viewmodel.GlosarioViewModel
import com.example.lexis.viewmodel.LexisViewModelFactory

@Composable
fun GlosarioRoute(
    alVolver: () -> Unit,
    viewModel: GlosarioViewModel = viewModel(factory = LexisViewModelFactory.glosario)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    GlosarioContent(
        uiState = uiState,
        onVolver = alVolver,
        onBusquedaChanged = viewModel::onBusquedaChanged,
        onReintentar = viewModel::cargarGlosario
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlosarioContent(
    uiState: GlosarioUiState,
    onVolver: () -> Unit,
    onBusquedaChanged: (String) -> Unit,
    onReintentar: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Glosario jurídico") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            OutlinedTextField(
                value = uiState.busqueda,
                onValueChange = onBusquedaChanged,
                label = { Text("Buscar término") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                val error = uiState.error
                val terminos = uiState.terminosFiltrados

                when {
                    uiState.cargando -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CircularProgressIndicator()
                            Text("Cargando glosario...")
                        }
                    }

                    error != null -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center).padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(error, color = MaterialTheme.colorScheme.error)
                            Button(onClick = onReintentar) { Text("Reintentar") }
                        }
                    }

                    terminos.isEmpty() -> {
                        Text(
                            text = if (uiState.terminos.isEmpty()) {
                                "No hay términos disponibles."
                            } else {
                                "No se encontraron términos con esa búsqueda."
                            },
                            modifier = Modifier.align(Alignment.Center).padding(24.dp)
                        )
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(terminos, key = { it.id }) { termino ->
                                Card(modifier = Modifier.fillMaxWidth()) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = termino.termino,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(termino.definicion)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}