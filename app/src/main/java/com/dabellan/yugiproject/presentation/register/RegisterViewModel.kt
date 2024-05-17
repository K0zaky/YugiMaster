package com.dabellan.yugiproject.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabellan.yugiproject.data.instances.RetrofitInstance
import com.dabellan.yugiproject.data.model.UsuarioItem
import kotlinx.coroutines.launch

sealed class RegisterState {
    object Loading : RegisterState()
    data class Success(val usuario: UsuarioItem) : RegisterState()
    data class Error(val message: String) : RegisterState()
}

class RegisterViewModel : ViewModel() {
    private val _registerState = MutableLiveData<RegisterState>()
    val registerState: LiveData<RegisterState> = _registerState

    fun register(usuario: UsuarioItem) {
        _registerState.value = RegisterState.Loading
        viewModelScope.launch {
            try {
                val newUser = RetrofitInstance.api.crearUsuario(usuario)
                _registerState.value = RegisterState.Success(newUser)
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}