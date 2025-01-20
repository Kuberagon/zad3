package com.example.giera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giera.ui.theme.GieraTheme
import gamescreen.CombatScreen
import gamescreen.GameScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GieraTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {
                    composable("login") { LoginScreen(navController) }
                    composable("home") { HomeScreen(navController) }
                    composable(
                        route = "game/{character}"
                    ) { backStackEntry ->
                        val character = backStackEntry.arguments?.getString("character") ?: "Zwiadowca"
                        GameScreen(navController = navController, character = character)
                    }
                    composable(
                        route = "combat/{monsterImage}"
                    ) { backStackEntry ->
                        val monsterImage = backStackEntry.arguments?.getString("monsterImage")?.toIntOrNull()
                            ?: R.drawable.monster1
                        CombatScreen(navController = navController, monsterImage = monsterImage)
                    }
                    composable("inventory") { InventoryScreen(navController) }
                    composable("game/defeat") {
                        DefeatScreen(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun DefeatScreen(navController: androidx.navigation.NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.skull),
            contentDescription = "Porażka",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Przegrałeś!",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                GameState.resetState() // Reset stanu gry
                navController.navigate("home") {
                    popUpTo("login") { inclusive = false }
                }
            }
        ) {
            Text(text = "Wróć do menu głównego")
        }
    }
}