package com.example.lexis.ui.perfil

import android.Manifest
import android.content.ActivityNotFoundException
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lexis.ui.components.AvatarUsuario
import com.example.lexis.viewmodel.LexisViewModelFactory
import com.example.lexis.util.cargarFotoReducida
import com.example.lexis.viewmodel.PerfilViewModel
import java.io.File

@Composable
fun PerfilRoute(
    idUsuario: Int,
    alVolver: () -> Unit,
    alCerrarSesion: () -> Unit,
    viewModel: PerfilViewModel = viewModel(factory = LexisViewModelFactory.perfil(idUsuario))
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.sesionCerrada) {
        if (uiState.sesionCerrada) alCerrarSesion()
    }

    PerfilContent(
        uiState = uiState,
        onFotoCapturada = viewModel::onFotoCapturada,
        onCamaraNoDisponible = viewModel::onCamaraNoDisponible,
        onPermisoCamaraDenegado = viewModel::onPermisoCamaraDenegado,
        onMensajeMostrado = viewModel::onMensajeMostrado,
        onCerrarSesionClick = viewModel::onCerrarSesionClick,
        onCancelarCerrarSesion = viewModel::onCancelarCerrarSesion,
        onConfirmarCerrarSesion = viewModel::onConfirmarCerrarSesion,
        onVolverClick = alVolver
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilContent(
    uiState: PerfilUiState,
    onFotoCapturada: (Bitmap) -> Unit,
    onCamaraNoDisponible: () -> Unit,
    onPermisoCamaraDenegado: () -> Unit,
    onMensajeMostrado: () -> Unit,
    onCerrarSesionClick: () -> Unit,
    onCancelarCerrarSesion: () -> Unit,
    onConfirmarCerrarSesion: () -> Unit,
    onVolverClick: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val contexto = LocalContext.current
    var archivoFoto by remember { mutableStateOf<File?>(null) }

    val lanzadorCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { fotoTomada ->
        archivoFoto?.let { archivo ->
            if (fotoTomada) cargarFotoReducida(archivo.absolutePath)?.let(onFotoCapturada)
            archivo.delete()
        }
    }

    val abrirCamara = {
        try {
            val archivo = File.createTempFile("foto_perfil", ".jpg", contexto.cacheDir)
            archivoFoto = archivo
            val uriFoto = FileProvider.getUriForFile(contexto, "${contexto.packageName}.provider", archivo)
            lanzadorCamara.launch(uriFoto)
        } catch (excepcion: ActivityNotFoundException) {
            onCamaraNoDisponible()
        }
    }

    val lanzadorPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permisoConcedido ->
        if (permisoConcedido) abrirCamara() else onPermisoCamaraDenegado()
    }

    LaunchedEffect(uiState.mensaje) {
        uiState.mensaje?.let {
            snackbarHostState.showSnackbar(it)
            onMensajeMostrado()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Mi perfil") },
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AvatarUsuario(
                iniciales = uiState.iniciales,
                rutaFoto = uiState.rutaFotoPerfil,
                tamano = 140.dp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = uiState.nombre,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = uiState.correo,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Tipo de usuario: ${uiState.tipoUsuario}",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { lanzadorPermiso.launch(Manifest.permission.CAMERA) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.PhotoCamera, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Tomar foto de perfil")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onCerrarSesionClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }
        }
    }

    if (uiState.mostrarModalCerrarSesion) {
        AlertDialog(
            onDismissRequest = onCancelarCerrarSesion,
            title = { Text("Cerrar sesión") },
            text = { Text("¿Seguro que quieres salir de tu cuenta?") },
            confirmButton = {
                Button(onClick = onConfirmarCerrarSesion) { Text("Salir") }
            },
            dismissButton = {
                TextButton(onClick = onCancelarCerrarSesion) { Text("Cancelar") }
            }
        )
    }
}
