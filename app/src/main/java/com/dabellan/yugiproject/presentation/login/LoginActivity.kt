package com.dabellan.yugiproject.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.dabellan.yugiproject.presentation.MainActivity
import com.dabellan.yugiproject.presentation.register.RegisterActivity
import com.dabellan.yugiproject.presentation.register.RegisterViewModel
import com.dabellan.yugiproject.ui.theme.YugiprojectTheme
import com.example.yugiproject.LoginScreen

class LoginActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YugiprojectTheme {
                val loginState by loginViewModel.loginState.observeAsState()
                val registerState by registerViewModel.registerState.observeAsState()
                var showRegisterDialog by remember { mutableStateOf(false) }

                if (showRegisterDialog) {
                    RegisterActivity(
                        registerState = registerState,
                        onRegister = { nick, email, password, image ->
                            registerViewModel.register(nick,email, password, image)
                        },
                        onDismiss = { showRegisterDialog = false }
                    )
                }

                LoginScreen(
                    loginState = loginState,
                    onLogin = { email, password ->
                        loginViewModel.login(email, password)
                    },
                    onNavigateToMain = {
                        navigateToMain()
                    },
                    onRegisterClick = {
                        showRegisterDialog = true
                    }
                )
            }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}