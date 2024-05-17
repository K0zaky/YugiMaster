package com.dabellan.yugiproject.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.dabellan.yugiproject.presentation.MainActivity
import com.dabellan.yugiproject.ui.theme.YugiprojectTheme
import com.example.yugiproject.LoginScreen

class LoginActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YugiprojectTheme {
                val loginState by loginViewModel.loginState.observeAsState()
                LoginScreen(
                    loginState = loginState,
                    onLogin = { email, password ->
                        loginViewModel.login(email, password)
                    },
                    onNavigateToMain = {
                        navigateToMain()
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