package com.dabellan.yugiproject.presentation

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
import com.dabellan.yugiproject.presentation.fragments.HomeFragment
import com.dabellan.yugiproject.presentation.fragments.HomeViewModel

class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()

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
            /*
                CurrentScreen.Settings*/
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
                composable(CurrentScreen.Home.route) { HomeFragment(homeViewModel, navController) }
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
    /*
    data object Settings : CurrentScreen(Constants.SETTINGS, Icons.Filled.Settings)*/
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