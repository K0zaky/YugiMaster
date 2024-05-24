package com.dabellan.yugiproject.presentation.fragments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dabellan.yugiproject.presentation.composables.normalizarTexto


@Composable
fun CarritoFragment(carritoViewModel: CarritoViewModel = viewModel(), perfilViewModel: PerfilViewModel = viewModel()) {
    val carritoItems by carritoViewModel.carritoItems.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.size(64.dp))

        if (carritoItems.isEmpty()) {
            Text(
                text = "Carrito vacío",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                items(carritoItems) { itemName ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Text(
                            text = normalizarTexto(itemName),
                            fontSize = 18.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.size(16.dp))

            Button(
                onClick = {
                    comprar(carritoItems, perfilViewModel)

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Comprar", fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.size(56.dp))
    }
}

private fun comprar(carritoItems: List<String>, perfilViewModel: PerfilViewModel) {
    perfilViewModel.guardarHistorialCompras(carritoItems)
}