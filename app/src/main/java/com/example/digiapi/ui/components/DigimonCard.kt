package com.example.digiapi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.digiapi.data.model.Digimon
import com.example.digiapi.ui.theme.*

@Composable
fun DigimonCard(
    digimon: Digimon,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (digimon.level) {
        "Fresh" -> FreshLevel
        "In Training" -> InTrainingLevel
        "Rookie" -> RookieLevel
        "Champion" -> ChampionLevel
        "Ultimate" -> UltimateLevel
        "Mega" -> MegaLevel
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen del Digimon
            AsyncImage(
                model = digimon.imageUrl,
                contentDescription = "Imagen de ${digimon.name}",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre del Digimon
            Text(
                text = digimon.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Nivel del Digimon
            LevelBadge(level = digimon.level)
        }
    }
}

@Composable
fun LevelBadge(level: String) {
    val (backgroundColor, textColor) = when (level) {
        "Fresh" -> Pair(Color(0xFF81C784), Color.White)
        "In Training" -> Pair(Color(0xFFFFB74D), Color.White)
        "Rookie" -> Pair(Color(0xFF64B5F6), Color.White)
        "Champion" -> Pair(Color(0xFFBA68C8), Color.White)
        "Ultimate" -> Pair(Color(0xFFE57373), Color.White)
        "Mega" -> Pair(Color(0xFFFFD54F), Color.Black)
        else -> Pair(MaterialTheme.colorScheme.primary, Color.White)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = level,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
    }
}