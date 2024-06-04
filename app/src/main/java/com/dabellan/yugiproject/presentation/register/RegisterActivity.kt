package com.dabellan.yugiproject.presentation.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun RegisterActivity(
    registerState: RegisterState?,
    onRegister: (String, String, String, String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var nick by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.background,
            contentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Registrar Usuario", style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(
                    value = nick,
                    onValueChange = { nick = it },
                    label = { Text("Nick") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )

                OutlinedTextField(
                    value = image,
                    onValueChange = { image = it },
                    label = { Text("Imagen URL") },
                    singleLine = true
                )

                Button(
                    onClick = {
                        val imageUrl = if (image.isEmpty()) {
                            "https://static.wikia.nocookie.net/yugioh/images/1/1a/MillenniumPuzzle-DSOD.png/revision/latest?cb=20170207182134"
                        } else {
                            image
                        }
                        if (nick==""||email==""||password==""){
                            Toast.makeText(context, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                        }else{
                            onRegister(nick, email, password, imageUrl)
                        }

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar")
                }

                when (registerState) {
                    is RegisterState.Loading -> CircularProgressIndicator()
                    is RegisterState.Error -> Text(
                        text = registerState.message,
                        color = MaterialTheme.colorScheme.error
                    )
                    is RegisterState.Success -> {
                        Text("Registro exitoso")
                        onDismiss()
                    }
                    else -> {}
                }
            }
        }
    }
}