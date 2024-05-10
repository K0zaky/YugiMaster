package com.dabellan.yugiproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dabellan.yugiproject.ui.theme.YugiprojectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YugiprojectTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent() {
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
        CardList()
    }
}

@Composable
fun CardList() {
    val nombres = (0..200).map { "Jaume" }
    LazyColumn {
        items(nombres) { nombre ->
            CardItem(nombre = nombre)
            Divider()
        }
    }
}

@Composable

//TODO Añadir valores a la función de tipo y atkdef/descripcion cuando se recojan de la API
fun CardItem(nombre: String) {
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
                .clip(shape = RectangleShape)
                .border(width = 1.dp, color = Color.Gray, shape = RectangleShape),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = nombre, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold) // Nombre en negrita
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Monstruo Normal", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "2400/2000", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppContentPreview() {
    YugiprojectTheme {
        AppContent()
    }
}