package gamescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.giera.R

@Composable
fun Player(tileSize: Dp, playerX: Int, playerY: Int, character: String) {
    val characterDrawable = when (character) {
        "Zwiadowca" -> R.drawable.gracz_1
        "Bialy rycerz" -> R.drawable.gracz_2
        else -> R.drawable.gracz_1
    }

    Box(
        modifier = Modifier
            .offset(
                x = (playerX * tileSize.value).dp,
                y = (playerY * tileSize.value).dp
            )
            .size(tileSize)
    ) {
        Image(
            painter = painterResource(id = characterDrawable),
            contentDescription = "Gracz"
        )
    }
}
