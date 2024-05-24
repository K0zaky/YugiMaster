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

    fun removeFromCarrito(itemName: String) {
        CarritoApplication.carrito.remove(itemName)
        _carritoItems.value = CarritoApplication.carrito
        println("Elemento eliminado del carrito: $itemName")
    }

    /*
    fun clearCarrito() {
        CarritoApplication.carrito.clear()
        _carritoItems.value = CarritoApplication.carrito
    }
    */
}