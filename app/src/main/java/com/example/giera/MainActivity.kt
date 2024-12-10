package com.example.giera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giera.ui.theme.GieraTheme
import gamescreen.GameScreen

class MainActivity : ComponentActivity() { // Główna klasa dla aktywności aplikacji
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GieraTheme { // motyw aplikacji
                val navController = rememberNavController() // Kontroler nawigacji

                NavHost(navController = navController, startDestination = "login") {
                    // Definiowanie nawigacji pomiędzy ekranami
                    composable("login") { LoginScreen(navController) } // Ekran logowania
                    composable("home") { HomeScreen(navController) } // Ekran główny
                    composable(
                        route = "game/{character}" // Trasa z parametrem "character"
                    ) { backStackEntry ->
                        val character = backStackEntry.arguments?.getString("character") ?: "Zwiadowca"
                        // Pobieranie nazwy wybranej postaci
                        GameScreen(navController = navController, character = character)
                        // Przejście do ekranu gry
                    }
                    composable("inventory") { InventoryScreen(navController) } // Ekran ekwipunku
                }
            }
        }
    }
}
