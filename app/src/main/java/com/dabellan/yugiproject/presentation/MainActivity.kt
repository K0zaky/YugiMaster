package com.dabellan.yugiproject.presentation

import DeckFragment
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dabellan.yugiproject.presentation.fragments.CarritoFragment
import com.dabellan.yugiproject.presentation.fragments.CarritoViewModel
import com.dabellan.yugiproject.presentation.fragments.DeckViewModel
import com.dabellan.yugiproject.presentation.fragments.HomeFragment
import com.dabellan.yugiproject.presentation.fragments.HomeViewModel
import com.dabellan.yugiproject.presentation.fragments.PerfilFragment
import com.dabellan.yugiproject.presentation.fragments.PerfilViewModel

class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val deckViewModel: DeckViewModel by viewModels()
    private val carritoViewModel: CarritoViewModel by viewModels()
    private val perfilViewModel: PerfilViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {
        val navController = rememberNavController()

        val screens = listOf(
            CurrentScreen.Home,

            CurrentScreen.Deck,

            CurrentScreen.Carrito,

            CurrentScreen.Perfil
        )

        Scaffold(
            topBar = {
                Surface {
                    TopAppBar(
                        title = { Text("Mercado") }
                    )
                }
            },
            bottomBar = {
                BottomNavigationBar(navController, screens)
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = CurrentScreen.Home.route
            ) {
                composable(CurrentScreen.Home.route) { HomeFragment(homeViewModel) }
                composable(CurrentScreen.Deck.route) { DeckFragment(deckViewModel) }
                composable(CurrentScreen.Carrito.route) { CarritoFragment(carritoViewModel) }
                composable(CurrentScreen.Perfil.route) { PerfilFragment(perfilViewModel) }
            }
        }
    }

    @Composable
    private fun BottomNavigationBar(
        navController: NavHostController,
        screens: List<CurrentScreen>
    ) {
        BottomNavigation(backgroundColor = Color.White) {
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStackEntry?.destination?.route

            screens.forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(screen.icon, contentDescription = null) },
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }


    sealed class CurrentScreen(val route: String, val icon: ImageVector) {
        data object Home : CurrentScreen("homeFragment", Icons.Filled.Home)
        data object Deck : CurrentScreen("deckFragment", Icons.Filled.Info)
        data object Carrito : CurrentScreen("carritoFragment", Icons.Filled.ShoppingCart)
        data object Perfil : CurrentScreen("perfilFragment", Icons.Filled.Person)
    }
}


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