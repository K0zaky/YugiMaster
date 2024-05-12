package com.dabellan.yugiproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.dabellan.yugiproject.Instances.RetrofitInstance
import com.dabellan.yugiproject.data.model.CartaItem

import com.dabellan.yugiproject.ui.theme.YugiprojectTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val service = RetrofitInstance.api

        lifecycleScope.launch {
            val cartas = service.getCartas()
            setContent {
                YugiprojectTheme {
                    AppContent(cartas)
                }
            }
        }

        enableEdgeToEdge()
    }
}

@Composable
fun AppContent(cartas: List<CartaItem>) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.Red)
        ) {
            Text(
                text = "Mercado",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        CardList(cartas)
    }
}

@Composable
fun CardList(cartas: List<CartaItem>) {
    LazyColumn {
        items(cartas) { carta ->
            CardItem(carta)
        }
    }
}

@Composable
fun CardItem(carta: CartaItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .border(width = 1.dp, color = Color.Gray)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = carta.nombre)
            Text(text = "Precio: ${carta.precio}")
        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun AppContentPreview() {
    YugiprojectTheme {
        AppContent()
    }
}*/