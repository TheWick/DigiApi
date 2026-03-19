package com.example.digiapi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.digiapi.ui.components.DigimonCard
import com.example.digiapi.ui.components.DigimonSearchBar
import com.example.digiapi.ui.components.LevelFilterChips
import com.example.digiapi.ui.components.digimonLevels
import com.example.digiapi.viewmodel.DigimonListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigimonListScreen(
    onDigimonClick: (String) -> Unit,
    viewModel: DigimonListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedLevel by viewModel.selectedLevel.collectAsState()
    val digimons by viewModel.digimons.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DigiDex") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = { viewModel.loadDigimons() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Recargar",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Barra de búsqueda
            DigimonSearchBar(
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                onClearQuery = { viewModel.onSearchQueryChange("") }
            )

            // Filtros de nivel
            LevelFilterChips(
                levels = digimonLevels,
                selectedLevel = selectedLevel,
                onLevelSelected = viewModel::filterByLevel,
                onClearFilter = viewModel::clearFilter
            )

            // Contenido principal
            when (val state = uiState) {
                is DigimonListViewModel.DigimonListUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is DigimonListViewModel.DigimonListUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Error: ${state.message}",
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.loadDigimons() }) {
                                Text("Reintentar")
                            }
                        }
                    }
                }

                is DigimonListViewModel.DigimonListUiState.Success -> {
                    if (state.digimons.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (searchQuery.isNotEmpty())
                                    "No se encontraron Digimons"
                                else
                                    "No hay Digimons disponibles",
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                items = state.digimons,
                                key = { it.name }
                            ) { digimon ->
                                DigimonCard(
                                    digimon = digimon,
                                    onClick = { onDigimonClick(digimon.name) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}