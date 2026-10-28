package com.example.lexis.ui.inicio
 
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.lexis.ui.theme.AmbarAcento
import com.example.lexis.ui.theme.AmbarClaro
import com.example.lexis.ui.theme.AmbarOscuro
import com.example.lexis.ui.theme.AzulClaro
import com.example.lexis.ui.theme.AzulLexis
import com.example.lexis.ui.theme.AzulProfundo
import com.example.lexis.ui.theme.BordeSuave
import com.example.lexis.ui.theme.FondoClaro
import com.example.lexis.ui.theme.GrisSuave
import com.example.lexis.ui.theme.TextoOscuro
import com.example.lexis.ui.theme.VerdeConectado
import com.example.lexis.viewmodel.InicioViewModel
import com.example.lexis.viewmodel.LexisViewModelFactory
 
@Composable
fun InicioRoute(
    idUsuario: Int,
    alIrAPerfil: () -> Unit,
    alIrALector: () -> Unit,
    viewModel: InicioViewModel = viewModel(factory = LexisViewModelFactory.inicio(idUsuario))
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
 
    // Al volver desde el perfil se recarga el usuario para mostrar su foto nueva
    LifecycleResumeEffect(Unit) {
        viewModel.cargarUsuario()
        onPauseOrDispose { }
    }
 
    InicioContent(
        uiState = uiState,
        onBusquedaChanged = viewModel::onBusquedaChanged,
        onAvatarClick = alIrAPerfil,
        onContinuarLecturaClick = alIrALector
    )
}
 
@Composable
fun InicioContent(
    uiState: InicioUiState,
    onBusquedaChanged: (String) -> Unit,
    onAvatarClick: () -> Unit,
    onContinuarLecturaClick: () -> Unit
) {
    // Animación de entrada de las tarjetas y de la barra de progreso
    var contenidoVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { contenidoVisible = true }
 
    val progresoAnimado by animateFloatAsState(
        targetValue = if (contenidoVisible) uiState.progresoLectura else 0f,
        animationSpec = tween(durationMillis = 1200),
        label = "progresoLectura"
    )
 
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
 
        AnimatedVisibility(
            visible = contenidoVisible,
            enter = fadeIn(tween(700)) + slideInVertically(tween(700)) { it / 3 }
        ) {
            TarjetaContinuarLeyendo(
                uiState = uiState,
                progreso = progresoAnimado,
                alHacerClick = onContinuarLecturaClick
            )
        }
 
        Spacer(modifier = Modifier.height(24.dp))
 
        SeccionMaterias(uiState = uiState)
 
        Spacer(modifier = Modifier.height(24.dp))
 
        SeccionCambiosRecientes(uiState = uiState)
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
private fun TarjetaContinuarLeyendo(
    uiState: InicioUiState,
    progreso: Float,
    alHacerClick: () -> Unit
) {
    Card(
        onClick = alHacerClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AzulProfundo),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        "CONTINUAR LEYENDO",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 10.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(Icons.Default.BookmarkBorder, contentDescription = "Guardar", tint = Color.White)
            }
 
            Spacer(modifier = Modifier.height(12.dp))
            Text(uiState.tituloUltimaLectura, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(uiState.articuloUltimaLectura, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
 
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(
                progress = { progreso },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = AmbarAcento,
                trackColor = Color.White.copy(alpha = 0.2f)
            )
 
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "${(progreso * 100).toInt()}% completado",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 11.sp
            )
        }
    }
}
 
@Composable
private fun SeccionMaterias(uiState: InicioUiState) {
    Text("EXPLORA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GrisSuave)
    Text("Materias jurídicas", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
 
    Spacer(modifier = Modifier.height(8.dp))
 
    val categoriasVisibles = uiState.categoriasFiltradas
    if (categoriasVisibles.isEmpty()) {
        Text("No hay materias que coincidan con tu búsqueda", fontSize = 12.sp, color = GrisSuave)
        return
    }
 
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        categoriasVisibles.forEach { categoria ->
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(AzulClaro),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("§", color = AzulLexis, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(categoria.titulo, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    Text(categoria.cantidadNormas, fontSize = 10.sp, color = GrisSuave)
                }
            }
        }
    }
}
 
@Composable
private fun SeccionCambiosRecientes(uiState: InicioUiState) {
    Text("AL DÍA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GrisSuave)
    Text("Cambios recientes", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
 
    Spacer(modifier = Modifier.height(8.dp))
 
    uiState.cambiosRecientes.forEach { cambio ->
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(cambio.dia, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    Text(cambio.mes, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GrisSuave)
                }
 
                Spacer(modifier = Modifier.width(16.dp))
 
                Column(modifier = Modifier.weight(1f)) {
                    Surface(color = AmbarClaro, shape = RoundedCornerShape(4.dp)) {
                        Text(
                            cambio.etiqueta,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            fontSize = 9.sp,
                            color = AmbarOscuro,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(cambio.titulo, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    Text(cambio.subtitulo, fontSize = 11.sp, color = GrisSuave)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(cambio.tiempoYCantidad, fontSize = 10.sp, color = GrisSuave)
                }
 
                Icon(Icons.Default.ChevronRight, contentDescription = "Ver más", tint = GrisSuave)
            }
        }
    }
}
