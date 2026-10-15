package com.example.lexis.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Checkbox
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var modal by remember { mutableStateOf(false) }
    var recordarUsuario by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lexis - App Juridica") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Correo")

            OutlinedTextField(
                value = username,
                onValueChange = { nuevoTexto ->
                    username = nuevoTexto
                },
                label = { Text("Introduce tu correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Contraseña")

            OutlinedTextField(
                value = password,
                onValueChange = { nuevoTexto ->
                    password = nuevoTexto
                },
                label = { Text("Introduce tu contraseña") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(42.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = recordarUsuario,
                        onCheckedChange = { activo -> recordarUsuario = activo }
                    )
                    Text(text = "Recordar")
                }

                TextButton(
                    onClick = {
                        /* hacer logica de boton olvidar contraseña - TODO */
                    }
                ) {
                    Text(text = "¿Olvidaste tu contraseña?")
                }
            }

            Button(
                onClick = { modal = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Ingresar")
            }

            if (modal) {
                AlertDialog(
                    onDismissRequest = { modal = false },
                    title = { Text("Mensaje") },
                    text = { Text("Usuario no registrado, debes registrar: $username con la contraseña:$password") },
                    confirmButton = {
                        Button(onClick = { modal = false }) {
                            Text("OK")
                        }
                    }
                )
            }

        }

    }
}