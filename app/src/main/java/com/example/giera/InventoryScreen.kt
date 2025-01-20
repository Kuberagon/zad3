package com.example.giera

import GameState
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore("game_preferences")

@Composable
fun InventoryScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val inventoryBackground = painterResource(id = R.drawable.inventory_background)

    var currentGold by remember { mutableIntStateOf(GameState.gold) }

    // Wczytaj złoto przy starcie ekranu
    LaunchedEffect(Unit) {
        context.dataStore.data.collectLatest { preferences ->
            val savedGold = preferences[intPreferencesKey("gold")] ?: 0
            currentGold = savedGold
            GameState.gold = savedGold
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()  // Box zajmuje cały ekran
            .background(Color(0xFFFAF3E0))  // Tło ekwipunku
    ) {
        // Używamy Modifier.fillMaxSize() dla obrazu, aby wypełniał cały ekran
        Image(
            painter = inventoryBackground,
            contentDescription = "Tło ekwipunku",
            modifier = Modifier.fillMaxSize(),  // To rozciąga obraz na cały ekran
            contentScale = ContentScale.FillBounds  // Dopasowanie obrazu do całego ekranu
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ekwipunek Gracza",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Złoto: $currentGold",
                    fontSize = 20.sp,
                    color = Color(0xFFFFD700)
                )

                Button(
                    onClick = {
                        if (currentGold >= 20 && !GameState.hasDagger) {
                            currentGold -= 20
                            GameState.gold = currentGold
                            GameState.hasDagger = true
                            scope.launch {
                                context.dataStore.edit { preferences ->
                                    preferences[intPreferencesKey("gold")] = currentGold
                                }
                            }
                        }
                    },
                    enabled = currentGold >= 20 && !GameState.hasDagger,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentGold >= 20 && !GameState.hasDagger)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = if (GameState.hasDagger)
                            "Sztylet zakupiony"
                        else
                            "Kup sztylet (20 złota)",
                        fontSize = 16.sp
                    )
                }
            }

            Button(
                onClick = {
                    navController.navigate("game/${GameState.character}") { // Wykorzystaj aktualny stan gry
                        popUpTo("inventory") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text(
                    text = "Powrót do gry",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}
