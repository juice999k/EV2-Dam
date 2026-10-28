package com.example.lexis.ui.lector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lexis.viewmodel.LectorViewModel

@Composable
fun LectorRoute(
    alVolver: () -> Unit,
    viewModel: LectorViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LectorContent(
        uiState = uiState,
        onAumentarLetra = viewModel::onAumentarLetra,
        onDisminuirLetra = viewModel::onDisminuirLetra,
        onVolverClick = alVolver
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LectorContent(
    uiState: LectorUiState,
    onAumentarLetra: () -> Unit,
    onDisminuirLetra: () -> Unit,
    onVolverClick: () -> Unit
) {
    val estadoLista = rememberLazyListState()

    val progreso by remember(uiState.articulos.size) {
        derivedStateOf {
            if (uiState.articulos.isEmpty()) 0f
            else (estadoLista.firstVisibleItemIndex + 1f) / uiState.articulos.size
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.tituloLey, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onVolverClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    TextButton(onClick = onDisminuirLetra) { Text("A-") }
                    TextButton(onClick = onAumentarLetra) { Text("A+") }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            LinearProgressIndicator(
                progress = { progreso },
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(
                state = estadoLista,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.articulos) { articulo ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Artículo ${articulo.numero} · ${articulo.titulo}",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = articulo.texto,
                                fontSize = uiState.tamanoLetra.sp,
                                lineHeight = (uiState.tamanoLetra * 1.5f).sp,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
