package com.dabellan.yugiproject.presentation.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabellan.yugiproject.data.instances.apiService
import com.dabellan.yugiproject.data.model.LogedUser
import com.dabellan.yugiproject.data.model.UsuarioItem
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {
    private val _userData = MutableLiveData<UsuarioItem>()
    val userData: LiveData<UsuarioItem> = _userData

    fun getUserData() {
        val id = LogedUser.userId.toString()
        viewModelScope.launch {
            val data = apiService.getUsuario(id)
            _userData.value = data
        }
    }
}