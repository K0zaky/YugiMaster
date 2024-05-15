package com.dabellan.yugiproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
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
    val navController = rememberNavController()

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
        NavHost(navController, startDestination = "cardListScreen") {
            composable("cardListScreen") {
                CardList(cartas, navController)
            }
            composable("detallesCarta/{cartaId}") { backStackEntry ->
                DetallesCarta(cartaId = backStackEntry.arguments?.getString("cartaId") ?: "")
            }
        }
    }
}

@Composable
fun CardList(cartas: List<CartaItem>, navController: NavHostController) {
    LazyColumn {
        items(cartas) { carta ->
            CardItem(carta, navController)
            Divider()
        }
    }
}

@Composable
fun CardItem(carta: CartaItem, navController: NavHostController) {
    val imageUrl = carta.imagen?.replace("\\", "")
    Card(
        modifier = Modifier.clickable {
            navController.navigate("detallesCarta/${carta.id}")
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
                Text(text = normalizarTexto(carta.nombre), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Text(text = "Código: ${carta.codigo}")
                Text(text = "Precio: ${carta.precio}€")
            }
        }
    }
}

@Composable
fun CoilImage(url: String?, modifier: Modifier = Modifier) {
    if (url != null) {
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


/*
@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Ruta.MainActivity.ruta){
        composable(route = Ruta.DetallesCarta.ruta, arguments = listOf(
            navArgument("name"){
                type = NavType.IntType

            }
        )){
            entry ->
            DetallesCarta(cartaId = )
        }
    }
}*/


/*
@Composable
fun detallesCarta(cartaId: Int) {
    Text(text = "pepino")
}
*/


/*
@Preview(showBackground = true)
@Composable
fun AppContentPreview() {
    YugiprojectTheme {
        AppContent()
    }
}*/