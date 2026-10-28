package com.example.lexis.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaColoresLexis = lightColorScheme(
    primary = AzulLexis,
    onPrimary = Color.White,
    primaryContainer = AzulClaro,
    onPrimaryContainer = AzulLexis,
    secondary = AmbarAcento,
    onSecondary = Color.White,
    background = FondoClaro,
    onBackground = TextoOscuro,
    surface = Color.White,
    onSurface = TextoOscuro,
    onSurfaceVariant = GrisSuave,
    outline = BordeSuave
)

@Composable
fun LexisTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaColoresLexis,
        typography = Typography,
        content = content
    )
}
