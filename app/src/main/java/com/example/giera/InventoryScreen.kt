package com.example.giera

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun InventoryScreen(navController: NavHostController) {
    val inventoryBackground = painterResource(id = R.drawable.inventory_background) // Wczytanie tła ekwipunku

    Column(
        modifier = Modifier
            .fillMaxSize() // Wypełnia cały ekran
            .background(Color(0xFFFAF3E0)) // Ustawia jasnobeżowe tło
    ) {
        Image(
            painter = inventoryBackground, // Ustawia obraz tła
            contentDescription = "Tło ekwipunku", // Opis obrazu dla dostępności
            modifier = Modifier.fillMaxSize(), // Wypełnia cały ekran
            contentScale = ContentScale.FillBounds // Rozciąga obraz na całą powierzchnię
        )

        Column(
            modifier = Modifier
                .fillMaxSize() // Układ wypełnia cały ekran
                .padding(16.dp), // Dodaje wewnętrzny odstęp
            verticalArrangement = Arrangement.SpaceBetween, // Rozkłada elementy równomiernie
            horizontalAlignment = Alignment.CenterHorizontally // Centruje elementy w poziomie
        ) {
            Spacer(modifier = Modifier.height(16.dp)) // Dodaje odstęp

            Text(
                text = "Ekwipunek Gracza", // Nagłówek ekwipunku
                fontSize = 24.sp, // Ustawia rozmiar tekstu
                color = Color.Black, // Ustawia kolor tekstu
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(), // Wypełnia całą szerokość
                horizontalArrangement = Arrangement.SpaceBetween // Rozkłada przyciski na boki
            ) {
                Button(
                    onClick = { navController.navigate("game") }, // Nawigacja z powrotem do gry
                    modifier = Modifier
                        .weight(1f) // Ustawia równą szerokość dla przycisków
                        .padding(8.dp), // Dodaje odstęp wewnętrzny
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green) // Zielony kolor przycisku
                ) {
                    Text(text = "Wyjdź i zapisz", fontSize = 16.sp, color = Color.White) // Tekst przycisku
                }

                Button(
                    onClick = { navController.navigate("login") }, // Nawigacja do ekranu logowania
                    modifier = Modifier
                        .weight(1f) // Ustawia równą szerokość dla przycisków
                        .padding(8.dp), // Dodaje odstęp wewnętrzny
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red) // Czerwony kolor przycisku
                ) {
                    Text(text = "Wyjdź", fontSize = 16.sp, color = Color.White) // Tekst przycisku
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Dodaje odstęp poniżej przycisków
        }
    }
}
