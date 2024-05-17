package com.dabellan.yugiproject.presentation.register

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.dabellan.yugiproject.presentation.composables.RegisterScreen
import com.dabellan.yugiproject.ui.theme.YugiprojectTheme

class RegisterActivity : ComponentActivity() {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YugiprojectTheme {
                val registerState by registerViewModel.registerState.observeAsState()
                RegisterScreen(
                    registerState = registerState,
                    onRegister = { usuario ->
                        registerViewModel.register(usuario)
                    }
                )
            }
        }
    }
}