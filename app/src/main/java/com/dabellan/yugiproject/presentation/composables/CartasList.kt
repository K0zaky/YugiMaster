package com.dabellan.yugiproject.presentation.composables

import androidx.compose.runtime.Composable
import com.dabellan.yugiproject.ViewModels.MainViewModel


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