package com.dabellan.yugiproject.presentation.fragments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CarritoFragment(carritoViewModel: CarritoViewModel = viewModel()) {

    val carritoItems by carritoViewModel.carritoItems.collectAsState()

    Column(Modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.size(64.dp))

        if (carritoItems.isEmpty()) {
            Text(
                text = "Carrito vacío",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            carritoItems.forEach { itemName ->
                Text(text = itemName)
            }
        }
        Spacer(modifier = Modifier.size(56.dp))
    }
}