package com.dabellan.yugiproject.presentation.fragments

import android.app.Application

class CarritoApplication : Application() {
    companion object {
        var carrito: MutableList<String> = mutableListOf()
    }
}