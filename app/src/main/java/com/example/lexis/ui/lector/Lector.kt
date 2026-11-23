package com.example.lexis.ui.lector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lexis.viewmodel.LectorViewModel
import com.example.lexis.viewmodel.LexisViewModelFactory

@Composable
fun LectorRoute(
    normaId: Int,
    alVolver: () -> Unit,
    viewModel: LectorViewModel = viewModel(
        factory = LexisViewModelFactory.lector(normaId)
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LectorContent(
        uiState = uiState,
        onAumentarLetra = viewModel::onAumentarLetra,
        onDisminuirLetra = viewModel::onDisminuirLetra,
        onVolverClick = alVolver,
        onReintentar = viewModel::cargarArticulos
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LectorContent(
    uiState: LectorUiState,
    onAumentarLetra: () -> Unit,
    onDisminuirLetra: () -> Unit,
    onVolverClick: () -> Unit,
    onReintentar: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.tituloLey.ifBlank { "Artículos" },
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVolverClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = onDisminuirLetra,
                        enabled = uiState.tamanoLetra > 12
                    ) {
                        Text("A−")
                    }

                    TextButton(
                        onClick = onAumentarLetra,
                        enabled = uiState.tamanoLetra < 26
                    ) {
                        Text("A+")
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

            when {
                uiState.cargando -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CircularProgressIndicator()
                        Text("Cargando artículos...")
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

                uiState.articulos.isEmpty() -> {
                    Text(
                        text = "Esta norma no tiene artículos disponibles.",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = uiState.articulos,
                            key = { articulo -> articulo.id }
                        ) { articulo ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor =
                                        MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = "Artículo ${articulo.numero} · ${articulo.titulo}",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                    Text(
                                        text = articulo.contenido,
                                        fontSize = uiState.tamanoLetra.sp,
                                        lineHeight =
                                            (uiState.tamanoLetra * 1.5f).sp,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}