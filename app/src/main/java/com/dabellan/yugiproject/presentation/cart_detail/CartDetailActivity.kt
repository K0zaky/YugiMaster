package com.dabellan.yugiproject.presentation.cart_detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dabellan.yugiproject.data.model.MagicaItem
import com.dabellan.yugiproject.data.model.MonstruoItem
import com.dabellan.yugiproject.data.model.TrampaItem
import com.dabellan.yugiproject.presentation.composables.CoilImage
import com.dabellan.yugiproject.presentation.composables.normalizarTexto
import com.dabellan.yugiproject.presentation.fragments.CarritoViewModel

class CartDetailActivity : ComponentActivity() {
    private val viewModel: CartDetailViewModel by viewModels()
    private val carritoViewModel: CarritoViewModel by viewModels()
    private lateinit var cartId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartId = intent.getIntExtra("cartId", -1).toString()
        viewModel.getCartById(cartId)

        viewModel.monstruoItem.observe(this) { monstruoItem ->
            if (monstruoItem != null) {
                setContent {
                    MonstruoContent(monstruoItem)
                }
            }
        }

        viewModel.magicaItem.observe(this) { magicaItem ->
            if (magicaItem != null) {
                setContent {
                    MagicaContent(magicaItem)
                }
            }
        }

        viewModel.trampaItem.observe(this) { trampaItem ->
            if (trampaItem != null) {
                setContent {
                    TrampaContent(trampaItem)
                }
            }
        }
    }

    @Composable
    fun MonstruoContent(monstruoItem: MonstruoItem) {
        Column {
            Text(text = normalizarTexto(monstruoItem.nombre))
            Text(text = normalizarTexto(monstruoItem.tipoMonstruo))
            Text(text = normalizarTexto(monstruoItem.atributo))
            Text(text = normalizarTexto(monstruoItem.efecto))
            CoilImage(url = monstruoItem.imagen, modifier = Modifier.size(500.dp))
            Button(onClick = {
                carritoViewModel.addToCarrito(monstruoItem.nombre)
            }) {
                Text(text = "Añadir al carrito")
            }
        }
    }

    @Composable
    fun MagicaContent(magicaItem: MagicaItem) {
        Column {
            Text(text = normalizarTexto(magicaItem.nombre))
            Text(text = normalizarTexto(magicaItem.tipoMagia))
            Text(text = normalizarTexto(magicaItem.efecto))
            CoilImage(url = magicaItem.imagen, modifier = Modifier.size(500.dp))
            Button(onClick = {
                carritoViewModel.addToCarrito(magicaItem.nombre)
            }) {
                Text(text = "Añadir al carrito")
            }
        }
    }

    @Composable
    fun TrampaContent(trampaItem: TrampaItem) {
        Column {
            Text(text = normalizarTexto(trampaItem.nombre))
            Text(text = normalizarTexto(trampaItem.tipoTrampa))
            Text(text = normalizarTexto(trampaItem.efecto))
            CoilImage(url = trampaItem.imagen, modifier = Modifier.size(500.dp))
            Button(onClick = {
                carritoViewModel.addToCarrito(trampaItem.nombre)
            }) {
                Text(text = "Añadir al carrito")
            }
        }
    }
}