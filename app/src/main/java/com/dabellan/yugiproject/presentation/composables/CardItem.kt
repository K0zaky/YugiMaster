package com.dabellan.yugiproject.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.dabellan.yugiproject.data.model.CartaItem

@Composable
fun CardItem(carta: CartaItem, onClick: () -> Unit) {
    val imageUrl = carta.imagen.replace("\\", "")
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(url = imageUrl, modifier = Modifier.size(80.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = normalizarTexto(carta.nombre),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Código: ${carta.codigo}")
                Text(text = "Precio: ${carta.precio}€")
            }
        }
    }
}

@Composable
fun CoilImage(url: String?, modifier: Modifier = Modifier) {
    url?.let {
        Image(
            painter = rememberImagePainter(
                data = url,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = null,
            modifier = modifier,
        )
    }
}

fun normalizarTexto(texto: String): String {
    return texto
        .replace("Ã¡", "á")
        .replace("Ã©", "é")
        .replace("Ã­", "í")
        .replace("Ã³", "ó")
        .replace("Ãº", "ú")
        .replace("Ã‰", "É")
        .replace("Ã¼", "ü")
        .replace("Ã±", "ñ")
}