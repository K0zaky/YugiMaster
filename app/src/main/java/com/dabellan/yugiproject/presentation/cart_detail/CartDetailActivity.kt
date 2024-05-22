package com.dabellan.yugiproject.presentation.cart_detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CoilImage(
                url = monstruoItem.imagen,
                modifier = Modifier
                    .size(450.dp)
                    .padding(bottom = 16.dp)
            )
            Text(
                text = normalizarTexto(monstruoItem.nombre),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(text = normalizarTexto(monstruoItem.tipoMonstruo), fontSize = 16.sp)
                Text(text = normalizarTexto(monstruoItem.atributo), fontSize = 16.sp)
                Text(text = normalizarTexto(monstruoItem.efecto), fontSize = 16.sp)
            }
            Button(
                onClick = {
                    carritoViewModel.addToCarrito(monstruoItem.nombre)
                },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(text = "Añadir al carrito")
            }
        }
    }

    @Composable
    fun MagicaContent(magicaItem: MagicaItem) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CoilImage(
                url = magicaItem.imagen,
                modifier = Modifier
                    .size(450.dp)
                    .padding(bottom = 16.dp)
            )
            Text(
                text = normalizarTexto(magicaItem.nombre),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(text = normalizarTexto(magicaItem.tipoMagia), fontSize = 16.sp)
                Text(text = normalizarTexto(magicaItem.efecto), fontSize = 16.sp)
            }
            Button(
                onClick = {
                    carritoViewModel.addToCarrito(magicaItem.nombre)
                },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(text = "Añadir al carrito")
            }
        }
    }

    @Composable
    fun TrampaContent(trampaItem: TrampaItem) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CoilImage(
                url = trampaItem.imagen,
                modifier = Modifier
                    .size(450.dp)
                    .padding(bottom = 16.dp)
            )
            Text(
                text = normalizarTexto(trampaItem.nombre),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(text = normalizarTexto(trampaItem.tipoTrampa), fontSize = 16.sp)
                Text(text = normalizarTexto(trampaItem.efecto), fontSize = 16.sp)
            }
            Button(
                onClick = {
                    carritoViewModel.addToCarrito(trampaItem.nombre)
                },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(text = "Añadir al carrito")
            }
        }
    }
}