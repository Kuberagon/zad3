package gamescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.material3.*
import androidx.navigation.NavHostController
import com.example.giera.R

@Composable
fun GameScreen(navController: NavHostController, character: String) {
    val tileSize = 32.dp // Ustawienie rozmiaru kafelków
    val mapSize = 10 // Rozmiar mapy w kafelkach

    var playerX by remember { mutableIntStateOf(5) } // Pozycja X gracza
    var playerY by remember { mutableIntStateOf(5) } // Pozycja Y gracza

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.map_grass), // Wczytanie obrazu mapy
            contentDescription = "Mapa gry",
            modifier = Modifier.fillMaxSize(), // Wypełnia cały ekran
            contentScale = ContentScale.FillBounds // Dopasowanie obrazu do ekranu
        )

        Player(
            tileSize = tileSize * 1.5f, // Powiększenie gracza o 1.5
            playerX = playerX, // Aktualna pozycja X gracza
            playerY = playerY, // Aktualna pozycja Y gracza
            character = if (character == "Zwiadowca") R.drawable.gracz_1 else R.drawable.gracz_2 // Wybór postaci
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp), // Margines od dołu
            contentAlignment = Alignment.BottomCenter // Pozycja na dole ekranu
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { if (playerY > 0) playerY-- }, modifier = Modifier.padding(4.dp)) {
                    Text("GÓRA", fontSize = 16.sp) // Przycisk do przesuwania w górę
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = { if (playerX > 0) playerX-- }, modifier = Modifier.padding(4.dp)) {
                        Text("LEWO", fontSize = 16.sp) // Przycisk w lewo
                    }
                    Spacer(modifier = Modifier.width(50.dp)) // Odstęp między przyciskami
                    Button(onClick = { if (playerX < mapSize - 1) playerX++ }, modifier = Modifier.padding(4.dp)) {
                        Text("PRAWO", fontSize = 16.sp) // Przycisk w prawo
                    }
                }
                Button(onClick = { if (playerY < mapSize - 1) playerY++ }, modifier = Modifier.padding(4.dp)) {
                    Text("DÓŁ", fontSize = 16.sp) // Przycisk w dół
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd // Pozycja w prawym górnym rogu
        ) {
            Button(
                onClick = { navController.navigate("inventory") }, // Przycisk otwierający ekwipunek
                modifier = Modifier
                    .padding(16.dp)
                    .size(120.dp, 50.dp) // Rozmiar przycisku
            ) {
                Text("Ekwipunek", fontSize = 16.sp) // Tekst przycisku
            }
        }
    }
}

@Composable
fun Player(tileSize: Dp, playerX: Int, playerY: Int, character: Int) {
    Box(
        modifier = Modifier
            .offset(
                x = (playerX * tileSize.value).dp, // Obliczenie pozycji X
                y = (playerY * tileSize.value).dp // Obliczenie pozycji Y
            )
            .size(tileSize) // Ustawienie rozmiaru gracza
    ) {
        Image(
            painter = painterResource(id = character), // Wczytanie odpowiedniego obrazu postaci
            contentDescription = "Gracz" // Opis obrazu
        )
    }
}
