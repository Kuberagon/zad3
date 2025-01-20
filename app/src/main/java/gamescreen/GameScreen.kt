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
import kotlin.random.Random

data class Monster(
    val x: Int,
    val y: Int,
    val imageResource: Int
)

@Composable
fun GameScreen(navController: NavHostController, character: String) {
    val tileSize = 32.dp
    val mapSize = 10

    var playerX by remember { mutableIntStateOf(5) }
    var playerY by remember { mutableIntStateOf(5) }

    // Lista potworów z ich pozycjami i obrazkami
    val monsters by remember {
        mutableStateOf(generateMonsters(mapSize))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Tło mapy
        Image(
            painter = painterResource(id = R.drawable.map_grass),
            contentDescription = "Mapa gry",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Rysowanie potworów
        monsters.forEach { monster ->
            MonsterSprite(
                tileSize = tileSize * 1.2f,
                monster = monster
            )
        }

        // Gracz
        Player(
            tileSize = tileSize * 1.5f,
            playerX = playerX,
            playerY = playerY,
            character = (if (character == "Zwiadowca") R.drawable.gracz_1 else R.drawable.gracz_2).toString()
        )

        // Przyciski ruchu
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = {
                        if (playerY > 0) {
                            playerY--
                            checkMonsterCollision(playerX, playerY, monsters) { monster ->
                                navController.navigate("combat/${monster.imageResource}")
                            }
                        }
                    },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text("GÓRA", fontSize = 16.sp)
                }

                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        onClick = {
                            if (playerX > 0) {
                                playerX--
                                checkMonsterCollision(playerX, playerY, monsters) { monster ->
                                    navController.navigate("combat/${monster.imageResource}")
                                }
                            }
                        },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text("LEWO", fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.width(50.dp))

                    Button(
                        onClick = {
                            if (playerX < mapSize - 1) {
                                playerX++
                                checkMonsterCollision(playerX, playerY, monsters) { monster ->
                                    navController.navigate("combat/${monster.imageResource}")
                                }
                            }
                        },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text("PRAWO", fontSize = 16.sp)
                    }
                }

                Button(
                    onClick = {
                        if (playerY < mapSize - 1) {
                            playerY++
                            checkMonsterCollision(playerX, playerY, monsters) { monster ->
                                navController.navigate("combat/${monster.imageResource}")
                            }
                        }
                    },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text("DÓŁ", fontSize = 16.sp)
                }
            }
        }

        // Przycisk ekwipunku
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            Button(
                onClick = { navController.navigate("inventory") },
                modifier = Modifier
                    .padding(16.dp)
                    .size(120.dp, 50.dp)
            ) {
                Text("Ekwipunek", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun MonsterSprite(tileSize: Dp, monster: Monster) {
    Box(
        modifier = Modifier
            .offset(
                x = (monster.x * tileSize.value).dp,
                y = (monster.y * tileSize.value).dp
            )
            .size(tileSize)
    ) {
        Image(
            painter = painterResource(id = monster.imageResource),
            contentDescription = "Potwór"
        )
    }
}

// Funkcje pomocnicze
private fun generateMonsters(mapSize: Int): List<Monster> {
    val monsterImages = listOf(R.drawable.monster1, R.drawable.monster2)
    return List(3) { // generuje 3 potwory
        Monster(
            x = Random.nextInt(mapSize),
            y = Random.nextInt(mapSize),
            imageResource = monsterImages[Random.nextInt(monsterImages.size)]
        )
    }
}

private fun checkMonsterCollision(
    playerX: Int,
    playerY: Int,
    monsters: List<Monster>,
    onCollision: (Monster) -> Unit
) {
    monsters.find { it.x == playerX && it.y == playerY }?.let { monster ->
        onCollision(monster)
    }
}