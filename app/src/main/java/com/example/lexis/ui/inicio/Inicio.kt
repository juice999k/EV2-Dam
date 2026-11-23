package com.example.lexis.ui.inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lexis.ui.components.AvatarUsuario
import com.example.lexis.ui.components.LogoAnimado
import com.example.lexis.ui.theme.AzulLexis
import com.example.lexis.ui.theme.BordeSuave
import com.example.lexis.ui.theme.FondoClaro
import com.example.lexis.ui.theme.GrisSuave
import com.example.lexis.ui.theme.TextoOscuro
import com.example.lexis.ui.theme.VerdeConectado
import com.example.lexis.viewmodel.InicioViewModel
import com.example.lexis.viewmodel.LexisViewModelFactory
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator

@Composable
fun InicioRoute(
    idUsuario: Int,
    alIrAPerfil: () -> Unit,
    alIrANormas: (Int) -> Unit,
    viewModel: InicioViewModel = viewModel(
        factory = LexisViewModelFactory.inicio(idUsuario)
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleResumeEffect(Unit) {
        viewModel.cargarUsuario()
        onPauseOrDispose { }
    }

    InicioContent(
        uiState = uiState,
        onBusquedaChanged = viewModel::onBusquedaChanged,
        onAvatarClick = alIrAPerfil,
        onReintentarCategorias = viewModel::cargarCategorias,
        onCategoriaClick = alIrANormas,
        onReintentarNovedades = viewModel::cargarNovedades
    )
}

@Composable
fun InicioContent(
    uiState: InicioUiState,
    onBusquedaChanged: (String) -> Unit,
    onAvatarClick: () -> Unit,
    onReintentarCategorias: () -> Unit,
    onCategoriaClick: (Int) -> Unit,
    onReintentarNovedades: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoClaro)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        EncabezadoInicio(uiState = uiState, onAvatarClick = onAvatarClick)

        Spacer(modifier = Modifier.height(20.dp))

        Text(uiState.saludo, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GrisSuave)
        Text("Hola, ${uiState.nombreUsuario}", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.textoBusqueda,
            onValueChange = onBusquedaChanged,
            singleLine = true,
            placeholder = { Text("Buscar materias jurídicas", fontSize = 13.sp, color = GrisSuave) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = GrisSuave) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = BordeSuave,
                focusedBorderColor = AzulLexis
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        SeccionMaterias(
            uiState = uiState,
            onReintentar = onReintentarCategorias,
            onCategoriaClick = onCategoriaClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        SeccionNovedades(
            uiState = uiState,
            onReintentar = onReintentarNovedades
        )
    }
}

@Composable
private fun EncabezadoInicio(uiState: InicioUiState, onAvatarClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            LogoAnimado(tamano = 40.dp)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text("LEXIS", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = AzulLexis)
                Text("APP JURÍDICA", fontSize = 9.sp, color = GrisSuave, fontWeight = FontWeight.Medium)
            }
        }

        Box {
            AvatarUsuario(
                iniciales = uiState.iniciales,
                rutaFoto = uiState.rutaFotoPerfil,
                tamano = 40.dp,
                alHacerClick = onAvatarClick
            )
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(VerdeConectado)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
private fun SeccionMaterias(
    uiState: InicioUiState,
    onReintentar: () -> Unit,
    onCategoriaClick: (Int) -> Unit
) {
    Text(
        text = "EXPLORA",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = GrisSuave
    )

    Text(
        text = "Materias jurídicas",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = TextoOscuro
    )

    Spacer(modifier = Modifier.height(8.dp))

    if (uiState.cargandoCategorias) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp,
                color = AzulLexis
            )
            Text(
                text = "Cargando materias...",
                fontSize = 13.sp,
                color = GrisSuave
            )
        }
        return
    }

    val error = uiState.errorCategorias

    if (error != null) {
        Text(
            text = error,
            fontSize = 13.sp,
            color = TextoOscuro
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onReintentar) {
            Text("Reintentar")
        }
        return
    }

    val categoriasVisibles = uiState.categoriasFiltradas

    if (categoriasVisibles.isEmpty()) {
        Text(
            text = if (uiState.categorias.isEmpty()) {
                "No hay materias disponibles."
            } else {
                "No hay materias que coincidan con tu búsqueda."
            },
            fontSize = 12.sp,
            color = GrisSuave
        )
        return
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        categoriasVisibles.forEach { categoria ->
            Card(
                onClick = { onCategoriaClick(categoria.id) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = categoria.titulo,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulLexis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = categoria.descripcion,
                        fontSize = 13.sp,
                        color = GrisSuave
                    )
                }
            }
        }
    }
}

@Composable
private fun SeccionNovedades(
    uiState: InicioUiState,
    onReintentar: () -> Unit
) {
    Text("AL DÍA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GrisSuave)
    Text("Novedades de la biblioteca", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)

    Spacer(modifier = Modifier.height(8.dp))

    if (uiState.cargandoNovedades) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp,
                color = AzulLexis
            )
            Text("Cargando novedades...", color = GrisSuave)
        }
        return
    }

    val error = uiState.errorNovedades
    if (error != null) {
        Text(error, color = TextoOscuro)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onReintentar) {
            Text("Reintentar novedades")
        }
        return
    }

    if (uiState.novedades.isEmpty()) {
        Text("No hay novedades disponibles.", color = GrisSuave)
        return
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        uiState.novedades.forEach { novedad ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = novedad.titulo,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulLexis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = formatearFechaNovedad(novedad.fecha),
                        fontSize = 11.sp,
                        color = GrisSuave
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = novedad.descripcion,
                        fontSize = 13.sp,
                        color = TextoOscuro
                    )
                }
            }
        }
    }
}

private fun formatearFechaNovedad(fecha: String): String =
    try {
        java.time.LocalDate.parse(fecha).format(
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
        )
    } catch (excepcion: java.time.format.DateTimeParseException) {
        fecha
    }
