package com.dabellan.yugiproject.presentation.fragments

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CarritoViewModel : ViewModel() {

    private val _carritoItems = MutableStateFlow<List<String>>(CarritoApplication.carrito)
    val carritoItems: StateFlow<List<String>> = _carritoItems.asStateFlow()

    fun addToCarrito(itemName: String) {
        CarritoApplication.carrito.add(itemName)
        _carritoItems.value = CarritoApplication.carrito
        println("Elementos en el carrito: ${CarritoApplication.carrito}")
    }

/*
    fun removeFromCarrito(itemName: String) {
        val currentList = _carritoItems.value.orEmpty().toMutableList()
        currentList.remove(itemName)
        _carritoItems.value = currentList
    }

    fun clearCarrito() {
        _carritoItems.value = emptyList()
    }*/
}