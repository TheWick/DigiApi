package com.example.digiapi.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LevelFilterChips(
    levels: List<String>,
    selectedLevel: String?,
    onLevelSelected: (String) -> Unit,
    onClearFilter: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = selectedLevel == null,
                onClick = onClearFilter,
                label = { Text("Todos") }
            )
        }

        items(levels) { level ->
            FilterChip(
                selected = selectedLevel == level,
                onClick = { onLevelSelected(level) },
                label = { Text(level) }
            )
        }
    }
}

val digimonLevels = listOf(
    "Fresh",
    "In Training",
    "Rookie",
    "Champion",
    "Ultimate",
    "Mega"
)