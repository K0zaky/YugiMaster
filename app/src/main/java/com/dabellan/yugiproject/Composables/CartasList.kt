package com.dabellan.yugiproject.Composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.dabellan.yugiproject.ViewModels.MainViewModel
import androidx.compose.runtime.collectAsState


@Composable
fun CartasList(viewModel: MainViewModel){
    //TODO: Arreglar la implementación de la API

   /* val cartas by viewModel.cartas.collectAsState()
    LazyColumn {
        items(cartas) {
            carta ->
            Text(text = carta.nombre)
        }
    }
    DisposableEffect(Unit) {
        viewModel.getCartas()
        onDispose { }
    }*/
}