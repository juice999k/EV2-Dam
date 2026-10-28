package com.example.lexis.ui.components

import android.graphics.BitmapFactory
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.lexis.R
import com.example.lexis.ui.theme.AzulLexis

@Composable
fun LogoAnimado(tamano: Dp, modifier: Modifier = Modifier) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    val transparencia by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "transparenciaLogo"
    )
    val escala by animateFloatAsState(
        targetValue = if (visible) 1f else 0.5f,
        animationSpec = tween(durationMillis = 800),
        label = "escalaLogo"
    )

    Image(
        painter = painterResource(id = R.drawable.logo_lexis),
        contentDescription = "Logo de Lexis",
        modifier = modifier
            .size(tamano)
            .graphicsLayer {
                alpha = transparencia
                scaleX = escala
                scaleY = escala
            }
    )
}

@Composable
fun AvatarUsuario(
    iniciales: String,
    rutaFoto: String?,
    tamano: Dp,
    modifier: Modifier = Modifier,
    alHacerClick: (() -> Unit)? = null
) {
    val foto = remember(rutaFoto) {
        rutaFoto?.let { BitmapFactory.decodeFile(it)?.asImageBitmap() }
    }
    val modificadorClick = if (alHacerClick != null) Modifier.clickable(onClick = alHacerClick) else Modifier

    Box(
        modifier = modifier
            .size(tamano)
            .clip(RoundedCornerShape(tamano / 4))
            .background(AzulLexis)
            .then(modificadorClick),
        contentAlignment = Alignment.Center
    ) {
        if (foto != null) {
            Image(
                bitmap = foto,
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Text(
                text = iniciales,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = (tamano.value / 2.8f).sp
            )
        }
    }
}

@Composable
fun CampoClave(
    valor: String,
    alCambiar: (String) -> Unit,
    etiqueta: String,
    mensajeError: String?,
    modifier: Modifier = Modifier
) {
    var mostrarClave by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = valor,
        onValueChange = alCambiar,
        label = { Text(etiqueta) },
        singleLine = true,
        isError = mensajeError != null,
        supportingText = mensajeError?.let { { Text(it) } },
        visualTransformation = if (mostrarClave) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { mostrarClave = !mostrarClave }) {
                Icon(
                    imageVector = if (mostrarClave) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (mostrarClave) "Ocultar contraseña" else "Mostrar contraseña"
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun ModalMensaje(
    titulo: String,
    mensaje: String,
    textoBoton: String = "Aceptar",
    alCerrar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = alCerrar,
        title = { Text(titulo) },
        text = { Text(mensaje) },
        confirmButton = {
            Button(onClick = alCerrar) { Text(textoBoton) }
        }
    )
}
