package com.dabellan.yugiproject.presentation.fragments.perfil

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dabellan.yugiproject.data.instances.apiService
import com.dabellan.yugiproject.data.model.LogedUser
import com.dabellan.yugiproject.data.model.UsuarioItem
import kotlinx.coroutines.launch

class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("historial_compras", Context.MODE_PRIVATE)
    private val _userData = MutableLiveData<UsuarioItem>()
    val userData: LiveData<UsuarioItem> = _userData

    private val _historialCompras = MutableLiveData<List<String>>()
    val historialCompras: LiveData<List<String>> = _historialCompras

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> = _navigateToLogin

    init {
        cargarHistorialCompras()
    }

    fun getUserData() {
        val id = LogedUser.userId.toString()
        viewModelScope.launch {
            val data = apiService.getUsuario(id)
            _userData.value = data
        }
    }

    fun guardarHistorialCompras(compras: List<String>) {
        val editor = sharedPreferences.edit()
        val currentHistorial = sharedPreferences.getStringSet(LogedUser.userId.toString(), mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        currentHistorial.addAll(compras)
        editor.putStringSet(LogedUser.userId.toString(), currentHistorial)
        editor.apply()
        _historialCompras.value = currentHistorial.toList()
    }

    fun cargarHistorialCompras() {
        val historial = sharedPreferences.getStringSet(LogedUser.userId.toString(), mutableSetOf())
        _historialCompras.value = historial?.toList()
    }

    fun eliminarUsuarioActual() {
        viewModelScope.launch {
            try {
                val response = apiService.deleteUsuario(LogedUser.userId)
                if (response.isSuccessful) {
                    sharedPreferences.edit().clear().apply()
                    _userData.value = null
                    LogedUser.userId = 0
                    _navigateToLogin.value = true
                } else {
                    // Manejar error
                }
            } catch (e: Exception) {
                // Manejar excepción
            }
        }
    }
}