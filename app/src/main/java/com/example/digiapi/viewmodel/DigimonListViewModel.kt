package com.example.digiapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digiapi.data.model.Digimon
import com.example.digiapi.data.repository.DigimonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DigimonListViewModel @Inject constructor(
    private val repository: DigimonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DigimonListUiState>(DigimonListUiState.Loading)
    val uiState: StateFlow<DigimonListUiState> = _uiState.asStateFlow()

    private val _digimons = MutableStateFlow<List<Digimon>>(emptyList())
    val digimons: StateFlow<List<Digimon>> = _digimons.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedLevel = MutableStateFlow<String?>(null)
    val selectedLevel: StateFlow<String?> = _selectedLevel.asStateFlow()

    init {
        loadDigimons()
    }

    fun loadDigimons() {
        viewModelScope.launch {
            _uiState.value = DigimonListUiState.Loading
            repository.getAllDigimons()
                .onSuccess { digimons ->
                    _digimons.value = digimons
                    _uiState.value = DigimonListUiState.Success(digimons)
                }
                .onFailure { error ->
                    _uiState.value = DigimonListUiState.Error(error.message ?: "Unknown error")
                }
        }
    }

    fun filterByLevel(level: String) {
        _selectedLevel.value = level
        viewModelScope.launch {
            _uiState.value = DigimonListUiState.Loading
            repository.getDigimonsByLevel(level)
                .onSuccess { digimons ->
                    _digimons.value = digimons
                    _uiState.value = DigimonListUiState.Success(digimons)
                }
                .onFailure { error ->
                    _uiState.value = DigimonListUiState.Error(error.message ?: "Unknown error")
                }
        }
    }

    fun clearFilter() {
        _selectedLevel.value = null
        loadDigimons()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        val currentList = when (val state = _uiState.value) {
            is DigimonListUiState.Success -> state.digimons
            else -> _digimons.value
        }

        val filtered = if (query.isBlank()) {
            currentList
        } else {
            currentList.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        _uiState.value = DigimonListUiState.Success(filtered)
    }

    sealed class DigimonListUiState {
        object Loading : DigimonListUiState()
        data class Success(val digimons: List<Digimon>) : DigimonListUiState()
        data class Error(val message: String) : DigimonListUiState()
    }
}