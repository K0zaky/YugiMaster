package com.example.yugiproject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dabellan.yugiproject.presentation.composables.CoilImage
import com.dabellan.yugiproject.presentation.login.LoginState
import com.dabellan.yugiproject.ui.theme.YugiprojectTheme

@Composable
fun LoginScreen(
    loginState: LoginState?,
    onLogin: (String, String) -> Unit,
    onNavigateToMain: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    YugiprojectTheme {

        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                CoilImage(
                    url = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/627fe721-846f-4f75-ac61-111ca00b27dd/daqj0nl-c26a7f4f-d9fd-4e51-bca4-c1d80cfca8ef.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzYyN2ZlNzIxLTg0NmYtNGY3NS1hYzYxLTExMWNhMDBiMjdkZFwvZGFxajBubC1jMjZhN2Y0Zi1kOWZkLTRlNTEtYmNhNC1jMWQ4MGNmY2E4ZWYucG5nIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.lCdGVlq7flrU5TKVn4LuwoUt5qzag1YiqbjoBv5kid8",
                    modifier = Modifier.size(180.dp)
                )
                Text(
                    text = "Bienvenido",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Inicia sesión",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Contraseña") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Default.Search else Icons.Default.Search
                        val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    }
                )
                Button(
                    onClick = {
                        onLogin(email, password)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Iniciar sesión")
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { onRegisterClick() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar")
                }

                loginState?.let {
                    when (it) {
                        is LoginState.Loading -> {
                            CircularProgressIndicator()
                        }
                        is LoginState.Success -> {
                            onNavigateToMain()
                        }
                        is LoginState.Error -> {
                            Text(text = it.message, color = Color.Red)
                        }
                    }
                }
            }
        }
    }

}