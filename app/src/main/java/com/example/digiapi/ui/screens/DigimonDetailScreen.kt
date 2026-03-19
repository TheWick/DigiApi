package com.example.digiapi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.digiapi.data.model.DigimonDetail
import com.example.digiapi.data.model.Evolution
import com.example.digiapi.ui.components.DigimonImage
import com.example.digiapi.ui.components.LevelBadge
import com.example.digiapi.viewmodel.DigimonDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigimonDetailScreen(
    onBackClick: () -> Unit,
    onEvolutionClick: (String) -> Unit,
    viewModel: DigimonDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del Digimon") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.retry() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Recargar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is DigimonDetailViewModel.DigimonDetailUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is DigimonDetailViewModel.DigimonDetailUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Error: ${state.message}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.retry() }) {
                            Text("Reintentar")
                        }
                    }
                }

                is DigimonDetailViewModel.DigimonDetailUiState.Success -> {
                    DigimonDetailContent(
                        digimon = state.digimon,
                        onEvolutionClick = onEvolutionClick
                    )
                }
            }
        }
    }
}

@Composable
fun DigimonDetailContent(
    digimon: DigimonDetail,
    onEvolutionClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Imagen principal
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                DigimonImage(
                    imageUrl = digimon.imageUrl,
                    contentDescription = digimon.name,
                    size = 250.dp
                )
            }
        }

        // Nombre y nivel
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = digimon.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                LevelBadge(level = digimon.level)
            }
        }

        // Información adicional
        item {
            InfoSection(title = "Información") {
                InfoRow(label = "Tipo", value = digimon.type ?: "Desconocido")
                InfoRow(label = "Atributo", value = digimon.attribute ?: "Desconocido")
                InfoRow(label = "Campo", value = digimon.field ?: "Desconocido")
            }
        }

        // Descripción
        if (!digimon.description.isNullOrEmpty()) {
            item {
                InfoSection(title = "Descripción") {
                    Text(
                        text = digimon.description,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }

        // Evoluciones previas
        if (!digimon.priorEvolutions.isNullOrEmpty()) {
            item {
                InfoSection(title = "Evoluciona de") {
                    EvolutionList(
                        evolutions = digimon.priorEvolutions,
                        onEvolutionClick = onEvolutionClick
                    )
                }
            }
        }

        // Evoluciones siguientes
        if (!digimon.evolutions.isNullOrEmpty()) {
            item {
                InfoSection(title = "Evoluciona a") {
                    EvolutionList(
                        evolutions = digimon.evolutions,
                        onEvolutionClick = onEvolutionClick
                    )
                }
            }
        }
    }
}

@Composable
fun InfoSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun EvolutionList(
    evolutions: List<Evolution>,
    onEvolutionClick: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        evolutions.forEach { evolution ->
            EvolutionItem(
                evolution = evolution,
                onClick = { onEvolutionClick(evolution.digimonName) }
            )
        }
    }
}

@Composable
fun EvolutionItem(
    evolution: Evolution,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = evolution.imageUrl,
                contentDescription = evolution.digimonName,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = evolution.digimonName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                if (!evolution.condition.isNullOrEmpty()) {
                    Text(
                        text = evolution.condition,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}