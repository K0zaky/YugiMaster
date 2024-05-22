package com.dabellan.yugiproject.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabellan.yugiproject.data.instances.RetrofitInstance
import com.dabellan.yugiproject.data.model.UsuarioItem
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel : ViewModel() {

    private val _registerState = MutableLiveData<RegisterState>()
    val registerState: LiveData<RegisterState> = _registerState

    fun register(nick: String, correo: String, contrasenya: String, imagen: String) {
        _registerState.value = RegisterState.Loading
        viewModelScope.launch {
            try {
                val usuario = UsuarioItem(nick = nick, correo = correo, contrasenya = contrasenya, imagen = imagen)
                val result = RetrofitInstance.api.crearUsuario(usuario)
                _registerState.value = RegisterState.Success(result)
            } catch (e: HttpException) {
                _registerState.value = RegisterState.Error("Error al registrar usuario")
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error("Error de red")
            }
        }
    }
}

sealed class RegisterState {
    data class Success(val usuario: UsuarioItem) : RegisterState()
    data class Error(val message: String) : RegisterState()
    object Loading : RegisterState()
}