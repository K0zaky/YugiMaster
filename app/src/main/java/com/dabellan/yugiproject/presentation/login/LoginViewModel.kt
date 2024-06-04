package com.dabellan.yugiproject.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabellan.yugiproject.data.instances.RetrofitInstance
import com.dabellan.yugiproject.data.model.LogedUser
import com.dabellan.yugiproject.data.model.UsuarioItem
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun login(correo: String, contrasenya: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val usuarios = RetrofitInstance.api.getUsers()
                val datos = usuarios.find { it.correo == correo.trim() && it.contrasenya == contrasenya }
                if (datos != null) {
                    LogedUser.userId = datos.id
                    _loginState.value = LoginState.Success(datos)
                } else {
                    _loginState.value = LoginState.Error("Datos incorrectos")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error de red")
            }
        }
    }
}

sealed class LoginState {
    data class Success(val usuario: UsuarioItem) : LoginState()
    data class Error(val message: String) : LoginState()
    object Loading : LoginState()
}
